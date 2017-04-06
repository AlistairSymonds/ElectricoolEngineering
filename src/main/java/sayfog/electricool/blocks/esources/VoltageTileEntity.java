/**
 * 
 */
package sayfog.electricool.blocks.esources;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sayfog.electricool.powersystem.ConnectionKeys;
import sayfog.electricool.powersystem.Voltage;

/**
 * @author alist
 *
 */
public class VoltageTileEntity extends Voltage {
	
	
	
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.setV(5);
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
