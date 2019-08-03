package shadows;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Light{
	
	private Ellipse2D ellipse;
	private Point2D t1 = new Point2D.Double(0, 0);
	private Point2D tp1 = new Point2D.Double(0, 0);
	private Point2D tp2 = new Point2D.Double(0, 0);
	private Point2D tp3 = new Point2D.Double(0, 0);
	private Point2D t2 = new Point2D.Double(0, 0);
	private Point2D t3 = new Point2D.Double(0, 0);
	private Point2D t4 = new Point2D.Double(0, 0);
	private Wall w;
	
	public Light(Wall w) { this.w = w; }
	public void on(int d, int x, int y, Graphics2D g){
		g.fill(maxwell(d, x, y, g));
	}
	
	public void on(int d, int x, int y, Graphics2D g, Area a){
		Area light = maxwell(d, x, y, g);
		light.subtract(a);
		g.fill(light);
	}

	private Area maxwell(int d, int x, int y, Graphics2D g){
		ellipse = new Ellipse2D.Double(x-d/2, y-d/2, d, d);
		Area light = new Area(ellipse);
		Area shadow = null;
		for(Rectangle2D b: w.brick){
			if(light.intersects(b)){
				shadow = this.addShadow(b);
				light.subtract(shadow);
			}
		}
		return light;
	}
	
	private Area addShadow(Rectangle2D b) {
		//de p1 à p4 ce sont les coins du carré
		Point2D p1 = new Point2D.Double(b.getX(), b.getY());
		Point2D p2 = new Point2D.Double(b.getX()+b.getWidth(), b.getY());
		Point2D p3 = new Point2D.Double(b.getX()+b.getWidth(), b.getY()+b.getHeight());
		Point2D p4 = new Point2D.Double(b.getX(), b.getY()+b.getHeight());
		
		//e est le centre d'ellipse
		double r = ellipse.getWidth()/2;
		Point2D.Double e = new Point2D.Double(ellipse.getX() + r, ellipse.getY() + r);
		
		//de d1 à d4 c'est la distance du centre d'ellipse au coins du carré
		double d1 = e.distance(p1);
		double d2 = e.distance(p2);
		double d3 = e.distance(p3);
		double d4 = e.distance(p4);
		
		//de t2 à t4 c'est les futurs coins du polygon
		
		
		if((e.getX()<p2.getX() && e.getX()>p1.getX()) || (e.getY()<p4.getY() && e.getY()>p1.getY())){
			if(e.getX()>p1.getX() && e.getX()<p2.getX() && e.getY()<p1.getY()){//haut
				createCoins(e, p1, p2, d1, d2, r, p4, p3);
			}else if(e.getX()>p1.getX() && e.getX()<p2.getX() && e.getY()>p4.getY()){//bas
				createCoins(e, p3, p4, d3, d4, r, p2, p1);
			}else if(e.getY()>p1.getY() && e.getY()<p4.getY() && e.getX()<p1.getX()){//gauche
				createCoins(e, p1, p4, d1, d4, r, p2, p3);
			}else if(e.getY()>p1.getY() && e.getY()<p4.getY() && e.getX()>p2.getX()){//droite
				createCoins(e, p2, p3, d2, d3, r, p1, p4);
			}
		}else{
			if(d1<d2 && d1<d3 && d1<d4){
				createCoins(e, p2, p4, d2, d4, r, p3, p3);
			}else if(d2<d1 && d2<d3 && d2<d4){
				createCoins(e, p3, p1, d3, d1, r, p4, p4);
			}else if(d3<d1 && d3<d2 && d3<d4){
				createCoins(e, p4, p2, d4, d2, r, p1, p1);
			}else if(d4<d1 && d4<d2 && d4<d3){
				createCoins(e, p1, p3, d1, d3, r, p2, p2);
			}
		}
		
		//tout ca on s'ent fou, juste je met dans un Area et je return, et Arc2D c'est pour plus tard
		Area returnArea = new Area();
		Polygon poly = new Polygon();
		poly.addPoint((int)t1.getX(), (int)t1.getY());
		poly.addPoint((int)tp2.getX(), (int)tp2.getY());
		poly.addPoint((int)t2.getX(), (int)t2.getY());
		poly.addPoint((int)t3.getX(), (int)t3.getY());
		poly.addPoint((int)tp1.getX(), (int)tp1.getY());
		poly.addPoint((int)tp3.getX(), (int)tp3.getY());
		poly.addPoint((int)t4.getX(), (int)t4.getY());
		
		Area area = new Area(poly);
		returnArea.add(area);
		return returnArea;
	}
	
	public void createCoins(Point2D e, Point2D p1, Point2D p2, double d1, double d2, double r, Point2D p3, Point2D p4){
		t1 = new Point2D.Double(e.getX()+(p2.getX()-e.getX())*((r/d2)+10), e.getY()+(p2.getY()-e.getY())*((r/d2)+10));
		t2 = new Point2D.Double(e.getX()+(p1.getX()-e.getX())*((r/d1)+10), e.getY()+(p1.getY()-e.getY())*((r/d1)+10));
		double zX = ((t1.getX()+t2.getX())/2);
		double zY = ((t1.getY()+t2.getY())/2);
		double zM = new Point2D.Double(zX, zY).distance(e);
		tp2 = new Point2D.Double(e.getX()+(zX-e.getX())*((r/zM)+10), e.getY()+(zY-e.getY())*((r/zM)+10));
		t3 = p1;
		t4 = p2;
		tp1 = p3;
		tp3 = p4;
	}

}
