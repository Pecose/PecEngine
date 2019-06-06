package pecose.billos.display;

import java.awt.geom.Point2D;

public class Position {
	private double x,y;
	public Point2D getPoint2D(){ return new Point2D.Double(x, y); }
	
	public Position(Point2D point, Window window){ this.position(point.getX(),point.getY(),window); }
	public Position(double x, double y, Window window){ this.position(x,y,window); }
	
	private void position(double x, double y, Window window){
		this.x = (x*100)/window.width;
		this.y = (y*100)/window.width;
	}

}
