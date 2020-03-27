package shadows;

import java.awt.geom.Point2D;

public class Vertice extends Point2D.Double{
	
	public int getIntX(){
		return (int) super.x;
	}
	
	public int getIntY(){
		return (int) super.y;
	}

	public Vertice(int x, int y){
		super(x, y);
	}

	public Vertice(double x, double y) {
		super(x, y);
	}
	
}
