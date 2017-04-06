package sayfog.electricool.blocks.passives;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sayfog.electricool.ModElectricool;
import sayfog.electricool.blocks.esources.VoltageTileEntity;
import sayfog.electricool.powersystem.AbstractComponentBlock;
import sayfog.electricool.powersystem.AbstractNetworkMemberBlock;
import sayfog.electricool.powersystem.AbstractNetworkMemberTileEntity;

public class ResistorBlock extends AbstractComponentBlock{

	public ResistorBlock() {
		super(Material.IRON);
		setUnlocalizedName(ModElectricool.MODID + ".resistor");
		setRegistryName("resistor");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		GameRegistry.registerTileEntity(ResistorTileEntity.class, ModElectricool.MODID + "_resistor");	
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new ResistorTileEntity();
	}	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, net.minecraft.entity.player.EntityPlayer playerIn, net.minecraft.util.EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote){
			//playerIn.addChatMessage(new TextComponentString("V drop " + getTE(world, pos).getVDrop()) );
		}
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel(){
		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
	@Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof AbstractNetworkMemberTileEntity) {
        	AbstractNetworkMemberTileEntity nte = (AbstractNetworkMemberTileEntity) te;
            currenttip.add(TextFormatting.GRAY + "Network: " + nte.getNetwork());
        }
        return currenttip;
    }
	
    private ResistorTileEntity getTE(World world, BlockPos pos){
    	return (ResistorTileEntity) world.getTileEntity(pos);
    }
}
