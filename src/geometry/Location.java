package geometry;

import java.awt.geom.Point2D;

public class Location extends Point2D.Double{
	public Location(int x, int y){ super(x, y); }
	public Location(double x, double y) { super(x, y); }
	public int getIntX(){ return (int) super.x; }
	public int getIntY(){ return (int) super.y; }
}
