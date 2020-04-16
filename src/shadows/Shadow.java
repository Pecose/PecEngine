package shadows;

import geometry.Polygon;
import geometry.Vertice;

public class Shadow extends Polygon{
	public void addPoint(double x, double y) {
		super.addPoint((int)x, (int)y);
	}

	public void addPoint(Vertice v) {
		super.addPoint((int)v.x, (int)v.y);
	}
}
