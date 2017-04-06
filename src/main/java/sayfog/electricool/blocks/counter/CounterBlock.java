package sayfog.electricool.blocks.counter;

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
import sayfog.electricool.ModElectricool;

public class CounterBlock extends Block implements ITileEntityProvider{
	
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	
	public CounterBlock(){
		super(Material.IRON);
		setUnlocalizedName(ModElectricool.MODID + ".counter");
		setRegistryName("counter");
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		GameRegistry.registerTileEntity(CounterTileEntity.class, ModElectricool.MODID + "_counter");
		
	}
	
	private CounterTileEntity getTE(World w, BlockPos pos){
		return (CounterTileEntity) w.getTileEntity(pos);
	}
	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, 
			EntityPlayer player, EnumHand hand, ItemStack heldItem, 
			EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		if(!world.isRemote){
			int count = -1;
			if(side == state.getValue(FACING)){
				CounterTileEntity te = getTE(world, pos);
				te.increment();
				count = te.getCount();
			} else {
				CounterTileEntity te = getTE(world, pos);
				count = te.getCount();
			}
			player.addChatComponentMessage(new TextComponentString("Count is : " + count));
		}
		return true;
	}
	
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new CounterTileEntity();
	}
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
        // Since we only allow horizontal rotation we need only 2 bits for facing. North, South, West, East start at index 2 so we have to add 2 here.
        return getDefaultState().withProperty(FACING, EnumFacing.getFront((meta & 3) + 2));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        // Since we only allow horizontal rotation we need only 2 bits for facing. North, South, West, East start at index 2 so we have to subtract 2 here.
        return state.getValue(FACING).getIndex()-2;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}
