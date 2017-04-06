package sayfog.electricool.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sayfog.electricool.ModElectricool;
import sayfog.electricool.powersystem.ElectricoolNetworkManager;

public class FirstBlock extends Block{

	public FirstBlock() {
		super(Material.CLAY);
		setUnlocalizedName(ModElectricool.MODID + ".firstblock");     // Used for localization (en_US.lang)
        setRegistryName("firstblock");        // The unique name (within your mod) that identifies this block
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, 
			EntityPlayer player, EnumHand hand, ItemStack heldItem, 
			EnumFacing side, float hitX, float hitY, float hitZ) 
	{
		return true;
	}
	@SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
