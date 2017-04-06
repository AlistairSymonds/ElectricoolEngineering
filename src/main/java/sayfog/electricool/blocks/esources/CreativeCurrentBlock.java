package sayfog.electricool.blocks.esources;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
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
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sayfog.electricool.ModElectricool;
import sayfog.electricool.blocks.counter.CounterTileEntity;
import sayfog.electricool.powersystem.AbstractComponentBlock;
import sayfog.electricool.powersystem.AbstractNetworkMemberBlock;

public class CreativeCurrentBlock extends AbstractComponentBlock{
	
	
	
	public CreativeCurrentBlock() {
		super(Material.IRON);
		setUnlocalizedName(ModElectricool.MODID + ".creativecurrent");
		setRegistryName("creativecurrent");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		GameRegistry.registerTileEntity(CounterTileEntity.class, ModElectricool.MODID + "_creativecurrent");	
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, 
			EntityPlayer player, EnumHand hand, ItemStack heldItem, 
			EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		if(!world.isRemote){
			System.out.println(world.getBlockState(pos));
		}
		return true;
		
	}
	
    
    @SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new CurrentTileEntity();
	}
}
