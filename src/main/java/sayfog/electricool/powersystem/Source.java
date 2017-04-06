package sayfog.electricool.powersystem;



public abstract class Source extends AbstractComponentTileEntity {
	double V = 0;
	double I = 0;
	double R = 0;
	
	
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
	 * @return the r
	 */
	public double getR() {
		return R;
	}
	/**
	 * @param r the r to set
	 */
	public void setR(double r) {
		R = r;
	}
}
