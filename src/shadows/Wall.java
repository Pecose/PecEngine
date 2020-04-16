package shadows;

import geometry.Polygon;

public class Wall extends Polygon{
	
	public Wall(){ super(); }
	public Wall(int x, int y){ super(); this.addPoint(x, y); }
	
	public Wall(int x, int y, int width, int height) {
		super();
		this.addPoint(x, y);
		this.addPoint(x + width, y);
		this.addPoint(x + width, y + height);
		this.addPoint(x, y + height);
	}
}
