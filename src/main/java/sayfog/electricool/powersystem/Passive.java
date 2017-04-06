package sayfog.electricool.powersystem;

public abstract class Passive extends AbstractComponentTileEntity {
	
	@Override
	public double getI(){
		double v = getVDrop();
		double r = getR();
		return v/r;
	}
}
