package pecose.billos.shadows;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Wall{
	 
	protected ArrayList<Rectangle2D> brick = new ArrayList<Rectangle2D>();
	
	public Wall(){}
	
	public void generate(int x,int y) { generate(x, y, 10, 10); }
	
	public void generate(int x,int y, int w, int h) {
		brick.add(new Rectangle2D.Double(x, y, w, h));
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
