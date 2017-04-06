package sayfog.electricool.powersystem;

import java.util.List;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public abstract class AbstractComponentTileEntity extends AbstractNetworkMemberTileEntity {
	public abstract List<String> getPossibleConnectionKeys();
	private double R = 0;
	private double I = 0;
	private double V = 0;
	
	public TileEntity getAdjTE(String ConnectionKey){
		List<String> conKeys = this.getPossibleConnectionKeys();
		if(!conKeys.contains(ConnectionKey)){
			throw new IllegalArgumentException();
		}
		
		EnumFacing facing = this.worldObj.getBlockState(getPos()).getValue(AbstractComponentBlock.FACING);
		
		if(ConnectionKey.equals(ConnectionKeys.POSITIVE)){
			return this.getWorld().getTileEntity(this.getPos().offset(facing));
			
		} else if (ConnectionKey.equals(ConnectionKeys.NEGATIVE)){
			return this.getWorld().getTileEntity(this.getPos().offset(facing.getOpposite()));
		}
		return null;
	}
	
	public double getVDrop(){
		double VDrop = 0;
		TileEntity posTE = getAdjTE(ConnectionKeys.POSITIVE);
		TileEntity negTE = getAdjTE(ConnectionKeys.NEGATIVE);
		if(posTE instanceof Connector && negTE instanceof Connector)
			{
				Connector posCon = (Connector) posTE;
				Connector negCon = (Connector) negTE;
				VDrop = posCon.getV() - negCon.getV(); 
			}
		return VDrop;
	}

	public double getV() {
		return V;
	}

	public void setV(double v) {
		V = v;
	}

	public double getI() {
		return I;
	}

	public void setI(double i) {
		I = i;
	}

	public double getR() {
		return R;
	}

	public void setR(double r) {
		R = r;
	}
}
