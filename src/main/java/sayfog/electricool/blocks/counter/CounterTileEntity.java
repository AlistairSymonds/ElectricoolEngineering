package sayfog.electricool.blocks.counter;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class CounterTileEntity extends TileEntity{
	private int count = 0;
	
	public int increment(){
		count++;
		markDirty();
		return count;
	}
	
	public int getCount(){
		return this.count;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		count = compound.getInteger("count");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("count", count);
		return compound;
	}
	
	
	
}
