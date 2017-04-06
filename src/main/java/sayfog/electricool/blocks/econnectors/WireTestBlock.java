package sayfog.electricool.blocks.econnectors;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sayfog.electricool.ModElectricool;
import sayfog.electricool.compat.WailaInfoProvider;
import sayfog.electricool.powersystem.AbstractNetworkMemberBlock;
import sayfog.electricool.powersystem.AbstractNetworkMemberTileEntity;
import sayfog.electricool.powersystem.ElectricoolNetwork;



public class WireTestBlock extends AbstractNetworkMemberBlock {
	public WireTestBlock() {
		super(Material.IRON);
		setUnlocalizedName(ModElectricool.MODID + ".wiretest");
		setRegistryName("wiretest");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		GameRegistry.registerTileEntity(WireTestTileEntity.class, ModElectricool.MODID + "_wiretest");		
	}

	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		
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
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	public WireTestTileEntity getTE(World world, BlockPos pos){
		return (WireTestTileEntity) world.getTileEntity(pos);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new WireTestTileEntity();
	}
	
}
