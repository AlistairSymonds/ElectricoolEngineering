package sayfog.electricool.proxy;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sayfog.electricool.*;

public class ClientProxy extends CommonProxy{
	 @Override
	    public void preInit(FMLPreInitializationEvent e) {
	        super.preInit(e);

	        // Typically initialization of models and such goes here:
	        EEBlocks.initModels();
	        EEItems.initModels();
	        
	    }
}
