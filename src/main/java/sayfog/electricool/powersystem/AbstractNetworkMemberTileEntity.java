package sayfog.electricool.powersystem;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public abstract class AbstractNetworkMemberTileEntity extends TileEntity {
	private ElectricoolNetwork network = null;
	
	public AbstractNetworkMemberTileEntity(){
		super();
	}
	
	public ElectricoolNetwork getNetwork(){
		return this.network;
	}
	
	protected ElectricoolNetwork setNetwork(ElectricoolNetwork networkIn){
		ElectricoolNetwork oldNet = this.network;
		this.network = networkIn;
		return oldNet;
	}
	
	@Override
	public String toString() {
		return (this.getClass().toString() + this.getPos().toString());
	}
}
