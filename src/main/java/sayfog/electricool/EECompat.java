package sayfog.electricool;

import net.minecraftforge.fml.common.Loader;
import sayfog.electricool.compat.WailaCompat;

public class EECompat {
	 public static void registerWaila() {
	        if (Loader.isModLoaded("Waila")) {
	            WailaCompat.register();
	        }
	 }
}
