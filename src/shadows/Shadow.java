package shadows;

public class Shadow extends Polygon2{
	public void addPoint(double x, double y) {
		super.addPoint((int)x, (int)y);
	}

	public void addPoint(Vertice v) {
		super.addPoint((int)v.x, (int)v.y);
	}
}
