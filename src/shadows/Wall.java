package shadows;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import shapes.Rectangle;

public class Wall{
	 
	public static ArrayList<Rectangle> brick = new ArrayList<Rectangle>();
	private Rectangle rectangle;
	private int posX, posY;
	
	public Wall(){}
	
	public void add(int x,int y) { add(x, y, 10, 10); }
	
	public void add(int x,int y, int w, int h) {
		brick.add(new Rectangle(x, y, w, h));
	}
	
	public boolean intersect(Area a){
		for(Rectangle2D b: this.brick){
			if(a.intersects(b)){ return true; }
		} return false;
	}
	
	public void fill(Graphics2D g){
		for(Rectangle2D r : brick){
			g.fill(r);
		}
	}

}
