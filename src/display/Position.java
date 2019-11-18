package display;

import java.awt.geom.Point2D;

import javax.swing.JFrame;

public class Position {
	private double x,y;
	public Point2D getPoint2D(){ return new Point2D.Double(x, y); }
	
	public Position(Point2D point, JFrame frame){ this.position(point.getX(),point.getY(),frame); }
	public Position(double x, double y, JFrame frame){ this.position(x,y,frame); }
	
	private void position(double x, double y, JFrame frame){
		this.x = (x*100)/((Frame)frame).getScreenWidth();
		this.y = (y*100)/((Frame)frame).getScreenWidth();
	}

}
