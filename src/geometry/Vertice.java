package geometry;

public class Vertice extends Location{
	
	public Vertice(int x, int y){ super(x, y); }
	public Vertice(double x, double y) { super(x, y); }
	public int getIntX(){ return (int) super.x; }
	public int getIntY(){ return (int) super.y; }
	
}
