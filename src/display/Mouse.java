package display;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.JFrame;

public class Mouse {
	
	private JFrame frame;
	public Mouse(JFrame frame){ this.frame = frame; }
	
	public int getX(){ return MouseInfo.getPointerInfo().getLocation().x - frame.getComponent(0).getX() - frame.getX(); };
	public int getY(){ return MouseInfo.getPointerInfo().getLocation().y - frame.getComponent(0).getY() - frame.getY(); };
	
	public Point2D getPosition(){ return new Position(getX(), getY(), frame).getPoint2D(); } 
}
