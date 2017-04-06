package sayfog.electricool.powersystem;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractComponentBlock extends AbstractNetworkMemberBlock {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	
	protected AbstractComponentBlock(Material materialIn) {
		super(materialIn);
	}

	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing dir = EnumFacing.getFacingFromVector(
        		(float)placer.getLookVec().xCoord, 
        		(float)placer.getLookVec().yCoord, 
        		(float)placer.getLookVec().zCoord);
		world.setBlockState(pos, state.withProperty(FACING, dir), 2);	
    }
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}
