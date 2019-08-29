package display;

import java.awt.MouseInfo;
import java.awt.geom.Point2D;

public class Mouse {
	private Window window;
	public Mouse(Window window){ this.window = window; }
	
	private double getX(){ return MouseInfo.getPointerInfo().getLocation().x - window.getComponent(0).getX() - window.getX(); };
	private double getY(){ return MouseInfo.getPointerInfo().getLocation().y - window.getComponent(0).getY() - window.getY(); };
	
	public Point2D getPosition(){ return new Position(getX(), getY(), window).getPoint2D(); } 
}
