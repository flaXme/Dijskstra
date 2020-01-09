package backend.phase.two;

public class Boundary {
	private int xMin;
	private int yMin;
	private int xMax;
	private int yMax;

	public Boundary(int xMin, int yMin, int xMax, int yMax) {
		super();
		this.xMin = xMin;
		this.yMin = yMin;
		this.xMax = xMax;
		this.yMax = yMax;
	}
	
	/**
	 * Test if given point (x,y) in Range of the Boundary
	 * @param x
	 * @param y
	 * @return
	 */
	boolean inRange(int x, int y) {
		return (x >= this.getxMin() && x <= this.getxMax()
				&& y >= this.getyMin() && y <= this.getyMax());
	}

	/**
	 * @return the xMin
	 */
	int getxMin() {
		return xMin;
	}

	/**
	 * @return the yMin
	 */
	int getyMin() {
		return yMin;
	}

	/**
	 * @return the xMax
	 */
	int getxMax() {
		return xMax;
	}

	/**
	 * @return the yMax
	 */
	int getyMax() {
		return yMax;
	}
	
	

}
