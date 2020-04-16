package shadows;

import java.awt.geom.Line2D;

public class Side extends Line2D.Double{

	public Side(double x, double y, double x2, double y2) {
		super(x, y, x2, y2);
	}

	public Side(Vertice p1, Vertice p2) {
        super(p1, p2);
    }

    public Vertice getP1() {
        return new Vertice(x1, y1);
    }

    public Vertice getP2() {
        return new Vertice(x2, y2);
    }

}
