package sayfog.electricool.items;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sayfog.electricool.ModElectricool;
import sayfog.electricool.powersystem.AbstractNetworkMemberTileEntity;
import sayfog.electricool.powersystem.Connector;
import sayfog.electricool.powersystem.Passive;
import sayfog.electricool.powersystem.Voltage;

public class MultimeterItem extends Item {
	public MultimeterItem(){
		setRegistryName("meteritem");        // The unique name (within your mod) that identifies this item
        setUnlocalizedName(ModElectricool.MODID + ".meteritem");     // Used for localization (en_US.lang)
        setMaxStackSize(1);
        GameRegistry.register(this);
	}
	
	@Nonnull
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(!worldIn.isRemote){
			Block block = worldIn.getBlockState(pos).getBlock();
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof AbstractNetworkMemberTileEntity){
				TextComponentString info = new TextComponentString(block.getLocalizedName() + "\n");
				String networkStr = "Network: " + ((AbstractNetworkMemberTileEntity) te).getNetwork().hashCode() + "\n";
				info.appendText(networkStr);
				
				if(te instanceof Passive){
					String vdropStr = "V Drop: " + ((Passive)te).getVDrop() + " [V]\n";
					String iStr = "Current: " + ((Passive)te).getI() + " [A]\n";
					String rStr = "Resistance: " + ((Passive)te).getR() + " [\u03A9]\n";
					info.appendText(vdropStr);
					info.appendText(iStr);
					info.appendText(rStr);
				}
				if(te instanceof Voltage){
					String vStr = "Voltage: " + ((Voltage)te).getV() + " [V]";
					info.appendText(vStr);
				}
				if(te instanceof Connector){
					String vStr;
//					if(((Connector) te).getV() < 0.0000f){
//						vStr = "Node is ground (0[V])";
//					} else {
//						vStr = "Node Voltage is: " + ((Connector) te).getV() + " [V]";
//					}
					vStr = "Node Voltage is: " + ((Connector) te).getV() + " [V] \n";
					info.appendText(vStr);
					
				}
				playerIn.addChatMessage(info);
			}
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
		
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		
	}
}
