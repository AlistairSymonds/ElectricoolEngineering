package sayfog.electricool;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sayfog.electricool.items.*;

public class EEItems {
	public static FirstItem firstItem;
	public static MultimeterItem multimeterItem;

    public static void init() {
        firstItem = new FirstItem();
        multimeterItem = new MultimeterItem();
    }
    
    @SideOnly(Side.CLIENT)
	public static void initModels() {
		firstItem.initModel();
		multimeterItem.initModel();
		
	}
}
