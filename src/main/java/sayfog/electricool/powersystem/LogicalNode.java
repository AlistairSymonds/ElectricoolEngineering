package sayfog.electricool.powersystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import net.minecraft.util.math.BlockPos;

public class LogicalNode {
	private HashMap<BlockPos, Connector> conSet;
	private ArrayList<Connector> conArray;
	private double nodeV = 0;
	
	public LogicalNode(){
		conSet = new HashMap<BlockPos, Connector>();
		conArray = new ArrayList<Connector>();
	}
	
	public Connector addCon(Connector connectorIn){
		conArray.add(connectorIn);
		return conSet.put(connectorIn.getPos(), connectorIn);
	}
	public boolean containsCon(Connector connectorIn){
		return conSet.containsKey(connectorIn.getPos());
	}
	public double getV(){
		return this.nodeV;
	}
	public void setV(double newV){
		nodeV = newV;
		Set<BlockPos> keys = conSet.keySet();
		for(BlockPos pos : keys){
			Connector con = conSet.get(pos);
			con.setV(newV);
			conSet.put(pos, con);
		}
	}
}
