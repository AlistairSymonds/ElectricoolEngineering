package sayfog.electricool.powersystem;

public abstract class Connector extends AbstractNetworkMemberTileEntity {
	private double I = 0;
	private double V = 0;
	
	
	
	/**
	 * @return the i
	 */
	public double getI() {
		return I;
	}
	/**
	 * @param i the i to set
	 */
	public void setI(double i) {
		I = i;
	}
	/**
	 * @return the v
	 */
	public double getV() {
		return V;
	}
	/**
	 * @param v the v to set
	 */
	public void setV(double v) {
		V = v;
	}
	
	
}
