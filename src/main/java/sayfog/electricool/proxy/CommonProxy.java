package sayfog.electricool.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sayfog.electricool.*;

public class CommonProxy {
	 public void preInit(FMLPreInitializationEvent e) {
	        // Initialization of blocks and items typically goes here:
	        EEBlocks.init();
	        EEItems.init();
	        
	    }

	    public void init(FMLInitializationEvent e) {

	    }

	    public void postInit(FMLPostInitializationEvent e) {

	    }
}
