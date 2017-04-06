package sayfog.electricool.blocks.passives;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import sayfog.electricool.powersystem.ConnectionKeys;
import sayfog.electricool.powersystem.Passive;

public class ResistorTileEntity extends Passive {
	
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.setR(10);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		return compound;
	}

	@Override
	public List<String> getPossibleConnectionKeys() {
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(ConnectionKeys.POSITIVE);
		keys.add(ConnectionKeys.NEGATIVE);
		return keys;
	}
}
