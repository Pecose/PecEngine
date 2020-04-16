package shadows;

import java.awt.Polygon;
import java.util.ArrayList;

public class Polygon2 extends Polygon{
	
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
	
//	public double perimeter() {
//        double perimeter = 0;
//        for (int i = 0; i < sommets.size(); i++)
//            longueur += getSommet(i).distance(getSommet(i + 1));
//        return longueur;
//    }
//
//    public double area() {
//        double aire = 0;
//        for (int i = 0; i < sommets.size(); i++)
//            aire += getSommet(i).x * getSommet(i + 1).y - getSommet(i + 1).x * getSommet(i).y;
//        return aire * 0.5;
//    }
//
//    public Point2D barycenter() {
//        double x = 0, y = 0;
//        final double k = 1 / (6 * aire());
//        for (int i = 0; i < sommets.size(); i++) {
//            double element = getSommet(i).x * getSommet(i + 1).y - getSommet(i + 1).x * getSommet(i).y;
//            x += ((getSommet(i).x + getSommet(i + 1).x) * element);
//            y += ((getSommet(i).y + getSommet(i + 1).y) * element);
//        }
//        return new Point(k * x, k * y);
//    }

}
