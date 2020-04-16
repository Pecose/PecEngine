package geometry;

import java.util.ArrayList;

import shadows.Wall;

public class Polygon extends java.awt.Polygon{
	
	public ArrayList<Vertice> getVertices() {
		 return Wall.getVertices(this);
	 }
	 
	 public static ArrayList<Vertice> getVertices(Polygon poly) {
		 ArrayList<Vertice> points = new ArrayList<Vertice>();
		 for(int i = 0; i < poly.npoints; i++){
			 points.add(new Vertice(poly.xpoints[i], poly.ypoints[i]));
		 }
		 return points;
	 }
	 
	 public ArrayList<Side> getSides() {
		 return Wall.getSides(this);
	 }
	 
	 public static ArrayList<Side> getSides(Polygon poly) {
		 ArrayList<Side> sides = new ArrayList<Side>();
		 for(int i = 1; i < poly.npoints; i++){
			 sides.add(new Side(poly.xpoints[i - 1], poly.ypoints[i - 1], poly.xpoints[i], poly.ypoints[i]));
		 }
		 sides.add(new Side(poly.xpoints[poly.npoints - 1], poly.ypoints[poly.npoints - 1], poly.xpoints[0], poly.ypoints[0]));
		 return sides;
	 }

	public void add(Vertice v) {
		this.addPoint(v.x, v.y);
	}

	private void addPoint(double x, double y) {
		super.addPoint((int)x, (int)y);
	}

	public Vertice get(int i) {
		return new Vertice(this.xpoints[i], this.ypoints[i]);
	}

	public int size() {
		return this.npoints;
	}
	
	public double perimeter() {
        double perimeter = 0;
        for (int i = 0; i < this.npoints; i++)
        	perimeter += this.get(i).distance(this.get(i + 1));
        return perimeter;
    }

    public double area() {
        double area = 0;
        for (int i = 0; i < this.npoints; i++)
        	area += this.get(i).x * this.get(i + 1).y - this.get(i + 1).x * this.get(i).y;
        return area * 0.5;
    }

    public Location barycenter() {
        double x = 0, y = 0;
        final double k = 1 / (6 * this.area());
        for (int i = 0; i < this.npoints; i++) {
            double element = this.get(i).x * this.get(i + 1).y - this.get(i + 1).x * this.get(i).y;
            x += ((this.get(i).x + this.get(i + 1).x) * element);
            y += ((this.get(i).y + this.get(i + 1).y) * element);
        }
        return new Location(k * x, k * y);
    }

}
