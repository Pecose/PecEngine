package display;

import java.awt.geom.Point2D;

public class Position {
	private double x,y;
	public Point2D getPoint2D(){ return new Point2D.Double(x, y); }
	
	public Position(Point2D point){ this.position(point.getX(),point.getY()); }
	public Position(double x, double y){ this.position(x,y); }
	
	private void position(double x, double y){
		this.x = (x*100)/Screen.getWidth();
		this.y = (y*100)/Screen.getWidth();
	}

}
