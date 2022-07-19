/**
 * 
 */
package ts.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class City {

	private int x;
	private int y;

	/**
	 * 
	 */
	public City(int x, int y) {
		setX(x);
		setY(y);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	public double distanceFrom(City city) {
		// Give difference in x,y
		double deltaXSq = Math.pow((city.getX() - this.getX()), 2);
		double deltaYSq = Math.pow((city.getY() - this.getY()), 2);
		// Calculate shortest path
		double distance = Math.sqrt(Math.abs(deltaXSq + deltaYSq));
		return distance;
	}

}
