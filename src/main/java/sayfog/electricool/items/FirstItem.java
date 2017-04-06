package sayfog.electricool.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sayfog.electricool.ModElectricool;

public class FirstItem extends Item{
	public FirstItem(){
		setRegistryName("firstitem");        // The unique name (within your mod) that identifies this item
        setUnlocalizedName(ModElectricool.MODID + ".firstitem");     // Used for localization (en_US.lang)
        GameRegistry.register(this);
	}
	
	
	@SideOnly(Side.CLIENT)
	public void initModel() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		
	}
	
	


}
