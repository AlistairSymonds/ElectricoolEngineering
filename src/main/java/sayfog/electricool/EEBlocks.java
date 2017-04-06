package sayfog.electricool;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sayfog.electricool.blocks.*;
import sayfog.electricool.blocks.counter.CounterBlock;
import sayfog.electricool.blocks.econnectors.WireTestBlock;
import sayfog.electricool.blocks.esources.*;
import sayfog.electricool.blocks.passives.ResistorBlock;

public class EEBlocks {
	public static FirstBlock firstBlock;
	public static CounterBlock counter;
	public static CreativeCurrentBlock current;
	public static CreativeVoltageBlock voltage;
	public static WireTestBlock wire;
	public static ResistorBlock resistorBlock;
	
    public static void init() {
        firstBlock = new FirstBlock();
        counter = new CounterBlock();
        current = new CreativeCurrentBlock();
        voltage = new CreativeVoltageBlock();
        wire  = new WireTestBlock();
        resistorBlock = new ResistorBlock();
    }
    
    @SideOnly(Side.CLIENT)
    public static void initModels(){
    	firstBlock.initModel();
    	counter.initModel();
    	current.initModel();
    	voltage.initModel();
    	wire.initModel();
    	resistorBlock.initModel();
    }
}
