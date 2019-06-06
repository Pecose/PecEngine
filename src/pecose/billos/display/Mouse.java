package pecose.billos.display;

import java.awt.MouseInfo;
import java.awt.geom.Point2D;

public class Mouse {
	
	private Window window;
	public Mouse(Window window){ this.window = window; }
	
	private double getX(){ return MouseInfo.getPointerInfo().getLocation().x - window.getLocationOnScreen().x; };
	private double getY(){ return MouseInfo.getPointerInfo().getLocation().y - window.getLocationOnScreen().y; };
	
	public Point2D getPosition(){ return new Position(getX(), getY(), window).getPoint2D(); } 
}
