package sayfog.electricool;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sayfog.electricool.proxy.CommonProxy;

import org.apache.logging.log4j.Logger;

@Mod(modid = ModElectricool.MODID, name = ModElectricool.MODNAME, version = ModElectricool.MODVERSION, dependencies = "required-after:Forge@[11.16.0.1865,)", useMetadata = true)
public class ModElectricool {
	
	
    public static final String MODID = "electricool";
    public static final String MODNAME = "Electricool";
    public static final String MODVERSION = "0.0.1";

    public boolean debugMode = false;
    
    @SidedProxy(clientSide = "sayfog.electricool.proxy.ClientProxy", serverSide = "sayfog.electricool.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static ModElectricool instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
