package sayfog.electricool.powersystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.ejml.simple.SimpleMatrix;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import sayfog.electricool.ModElectricool;

public class ElectricoolNetwork {
	
	private boolean dirtyNetwork = false;
	private ArrayList<AbstractNetworkMemberTileEntity> members = null;
	private ArrayList<Voltage> voltageSources = null;
	private ArrayList<LogicalNode> nodes = null;
	private Queue<AbstractComponentTileEntity> compQ = null;
	private ArrayList<AbstractComponentTileEntity> components = null;
	private LogicalNode currentNode;
	
	public ElectricoolNetwork(AbstractComponentTileEntity startingComponent){
		long startTime = System.nanoTime();
		
		nodes = new ArrayList<LogicalNode>();
		compQ = new ConcurrentLinkedQueue<AbstractComponentTileEntity>();
		members = new ArrayList<AbstractNetworkMemberTileEntity>();
		voltageSources = new ArrayList<Voltage>();
		components = new ArrayList<AbstractComponentTileEntity>();
		
		networkCreation(startingComponent);
		ModElectricool.logger.info("Network Traversal Complete");
		ElectricoolNetworkManager.addNetwork(this);
		
		compQ = null;
		
		long finishTime = System.nanoTime();
		long timeTaken = finishTime - startTime;
		double timeTakenMillis = timeTaken / 1000000.0;
		ModElectricool.logger.info("Created a network in " + timeTakenMillis + " [ms]");
	}
	
	public void addMember(AbstractNetworkMemberTileEntity member){
		
		if(member instanceof Voltage && !voltageSources.contains(member)){
			voltageSources.add((Voltage) member);
		}
		if(member instanceof Connector){
			currentNode.addCon((Connector) member);
		}
		
		if(member instanceof AbstractComponentTileEntity && !members.contains(member)){
			components.add((AbstractComponentTileEntity) member);
		}
		
		members.add(member);
		member.setNetwork(this);
		
		if(member instanceof AbstractComponentTileEntity && !members.contains(member)){
			compQ.add((AbstractComponentTileEntity) member);
		}
	}
	
	private void networkCreation(AbstractComponentTileEntity startingComponent){
		currentNode = new LogicalNode();
		for(String conKey : startingComponent.getPossibleConnectionKeys()){
			TileEntity adjTE = startingComponent.getAdjTE(conKey);
			if(adjTE instanceof Connector){
				wireTraversal((Connector) adjTE);
				nodes.add(currentNode);
				currentNode = new LogicalNode();
			}
		}
		
		while(!compQ.isEmpty()){
			AbstractComponentTileEntity nextComp = compQ.poll();
			for(String conKey : nextComp.getPossibleConnectionKeys()){
				TileEntity adjTE = nextComp.getAdjTE(conKey);
				if(adjTE instanceof Connector){
					addMember(nextComp);
					wireTraversal((Connector)adjTE);
					nodes.add(currentNode);
					currentNode = new LogicalNode();
				}
			}
		}
	}
	
	private void wireTraversal(Connector curCon){
		if(curCon.getNetwork() == this){
			return;
		}
		addMember(curCon);
		for(EnumFacing f : EnumFacing.VALUES){
			
			if(curCon.getWorld().getTileEntity( curCon.getPos().offset(f) )
					instanceof Connector)
			{
				Connector adjCon = (Connector) curCon.getWorld().getTileEntity( curCon.getPos().offset(f));
				if(adjCon.getNetwork() != curCon.getNetwork())
				{
					wireTraversal(adjCon);
				}
				
			} 
			
			else if (curCon.getWorld().getTileEntity( curCon.getPos().offset(f) )
					instanceof AbstractComponentTileEntity)
			{
				AbstractComponentTileEntity adjComp = (AbstractComponentTileEntity) curCon.getWorld().getTileEntity( curCon.getPos().offset(f));
				for(String key : adjComp.getPossibleConnectionKeys())
				{
					TileEntity adjTE = adjComp.getAdjTE(key);
					if(adjTE == curCon && adjTE instanceof Connector && adjComp.getNetwork() != this)
					{
						addMember(adjComp);
					}
				}
			}
		}
	}
	
	public void calculateValuesMNA() {
		
		long MNAstart = System.nanoTime();
		/*MNA
		[A] x [x] = [z]
		x = unknowns
		therefore 
		1. create [A]
		2. create [z]
		3. invert [A]
		4. x = [A]^1 dot [z]
		
		M voltage sources
		N non-ground nodes 
		*/
		
		int N = nodes.size()-1;
		int M = voltageSources.size();

		SimpleMatrix GMatrix = new SimpleMatrix(N, N);
		//generate diagonals from sum of conductances connected to a node
		for(int i = 0; i < N; i++){
			LogicalNode node = nodes.get(i+1);
			ArrayList<AbstractComponentTileEntity> attachedComps = new ArrayList<AbstractComponentTileEntity>();
			for(AbstractComponentTileEntity testComp : components){
				for(String s : testComp.getPossibleConnectionKeys()){
					if(testComp.getAdjTE(s) instanceof Connector){
						if(node.containsCon((Connector) testComp.getAdjTE(s))){
							attachedComps.add(testComp);
						}
					}
				}
			}
			
			double totalG = 0;
			for(AbstractComponentTileEntity comp : attachedComps){
				if(comp.getR() > 0){
					totalG = totalG + (1/comp.getR());
				}
			}
			GMatrix.set(i, i, totalG);
			
		}
		 
		long gmatrixDiagonals = System.nanoTime();
		for(AbstractComponentTileEntity comp : components){
			if(comp instanceof Passive){
				List<Integer> indicies = getAttachedNodes(comp);
				if(indicies.size() == 2){
					if(indicies.get(0).intValue() != 0 && 
							indicies.get(1).intValue() != 0 &&
							indicies.get(0).intValue() < N &&
							indicies.get(1).intValue() < N)
					{
						GMatrix.set(indicies.get(0).intValue() - 1, indicies.get(1).intValue() - 1, (-1)/(comp.getR()));
						GMatrix.set(indicies.get(1).intValue() - 1, indicies.get(0 ).intValue() - 1, (-1)/(comp.getR()));
					}
				}
			}
		}
		long gmatrixOffDiag = System.nanoTime();
		ModElectricool.logger.info(GMatrix);
		
		SimpleMatrix BMatrix = new SimpleMatrix(N, M);
		for(int i = 0; i < voltageSources.size(); i++){
			Voltage v = voltageSources.get(i);
			for(String s : v.getPossibleConnectionKeys()){
				for(int j = 0; j < nodes.size(); j++){
					if(v.getAdjTE(s) instanceof Connector && nodes.get(j).containsCon((Connector) v.getAdjTE(s))){
						if(j < N && i < M){
							if(s == ConnectionKeys.POSITIVE){
								BMatrix.set(j, i, 1);
							} else if(s == ConnectionKeys.NEGATIVE){
								BMatrix.set(j, i, -1);
							}
						}
						
					}
				}
			}
		}
		
		
		SimpleMatrix CMatrix = BMatrix.transpose();
		SimpleMatrix AMatrix = new SimpleMatrix(M+N,M+N);
		AMatrix.insertIntoThis(0, 0, GMatrix);
		AMatrix.insertIntoThis(0, N, BMatrix);
		AMatrix.insertIntoThis(N, 0, CMatrix);
		ModElectricool.logger.info(AMatrix);
		
		SimpleMatrix iMatrix = new SimpleMatrix(N, 1);
		
		SimpleMatrix eMatrix = new SimpleMatrix(M, 1);
		for(int i = 0; i < voltageSources.size(); i++){
			eMatrix.set(i, 0, voltageSources.get(i).getV());
		}
		SimpleMatrix zMatrix = new SimpleMatrix(M+N, 1);
		zMatrix.insertIntoThis(0, 0, iMatrix);
		zMatrix.insertIntoThis(N, 0, eMatrix);
		
		SimpleMatrix xMatrix = AMatrix.invert().mult(zMatrix);
		xMatrix.print();
		for(int i = 0; i < N; i++){
			nodes.get(i+1).setV(xMatrix.get(i, 0));
		}
		for(int i = N; i < M; i++){
			voltageSources.get(i).setI(xMatrix.get(i, 0));
		}
		
		
		
		
		
	}
	
	private ArrayList<Integer> getAttachedNodes(AbstractComponentTileEntity comp){
		ArrayList<Integer> indicies = new ArrayList<Integer>();
		for(String conKey : comp.getPossibleConnectionKeys()){
			TileEntity adjTE = comp.getAdjTE(conKey);
			if(adjTE instanceof Connector){
				Connector adjCon = (Connector) adjTE;
				for(int i = 0; i < nodes.size(); i++){
					if(nodes.get(i).containsCon(adjCon)){
						indicies.add(i);
					}
				}
			}
		}
		return indicies;
	}
	
	
	
	
	
}
