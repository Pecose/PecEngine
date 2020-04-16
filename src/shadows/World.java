package shadows;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import display.Screen;
import geometry.Polygon;
import geometry.Side;
import geometry.Vertice;

public class World extends BufferedImage{
	
	private Graphics2D graphics;
	private Graphics2D graphicsTarget;
	private HashMap<String, Light> lights = new HashMap<String, Light>();
	private HashMap<String, Wall> walls = new HashMap<String, Wall>();
	private int alpha = 0;

	public void addLight(String name, Light light) { lights.put(name, light); }
	public void addWall(String name, Wall wall) { walls.put(name, wall); }
	
	public Light getLight(String name) { return lights.get(name); }
	public Wall getWall(String name) { return walls.get(name); }
	
	public void setLight(String name, Light light) { lights.replace(name, light); }
	public void setWall(String name, Wall wall) { walls.replace(name, wall); }
	
	public void supLight(String name) { lights.remove(name); }
	public void supWall(String name) { walls.remove(name); }
	
	public void setTwilight(int alpha){ this.alpha = alpha; }
	
	public Set<Entry<String, Wall>> getWallsSet(){
		return walls.entrySet();
	}
	
	public Graphics2D getGraphics() { return graphics; }
	
	public World(Graphics2D graphicsTarget) {
		super(Screen.getWidth(), Screen.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.graphicsTarget = graphicsTarget;
		this.graphics = this.createGraphics();
		this.graphics.setComposite(BlendComposite.Add);
		this.graphics.setRenderingHints(new RenderingHints( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
	}

	public void displayLights() {
		
		this.graphics.setColor(new Color(0, 0, 0, alpha));
		this.graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		for(Entry<String, Light> light : lights.entrySet()){
			this.displayLight(light.getValue());
		}
		
		this.reverseAlpha();
		this.graphicsTarget.drawImage(this, 0, 0, null);
	}
	
	public void displayLight(Light light) {
		//afficher dans un Area pour supprimer les parties ombragées puis afficher l'Area.
		this.graphics.setPaint(light.getGradient());
		
		Area area = new Area(light.getEllipse2D());
		for(Wall wall : walls.values()){
			area.subtract(this.addShadow(light, wall));
		}
		
		this.graphics.fill(area);
	}
	
	public void reverseAlpha() {
		int[] data = ((DataBufferInt)this.getAlphaRaster().getDataBuffer()).getData();
		for(int i = 0; i < data.length; i++) {
			data[i] = data[i] & 0xff000000 ^ 0xff000000 | data[i] & 0xffffff;
		}
	}
	
	private Area addShadow(Light light, Wall wall) {
		
		//Représante les ombres
		Area area = new Area();
		
		//Liste des ombres portées des coins visibles
		Polygon dropShadows = this.getDropShadows(light, wall);
		
		ArrayList<Side> shadowsSides = Polygon.getSides(dropShadows);
		ArrayList<Side> visibleSides = Polygon.getSides(wall);

		for(int i = 0; i < dropShadows.size(); i++){
			Polygon polygon = new Polygon();
			
			Vertice p1 = shadowsSides.get(i).getP1();
			Vertice p2 = shadowsSides.get(i).getP2();
			Vertice p3 = visibleSides.get(i).getP1();
			Vertice p4 = visibleSides.get(i).getP2();
			
			Vertice barycenter = new Vertice((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
			Vertice dropShadowBarycenter = this.getDropShadow(light, barycenter);
			Vertice add = new Vertice((dropShadowBarycenter.x - barycenter.x), (dropShadowBarycenter.y - barycenter.y));
			
			Vertice p5 = new Vertice(p1.x + add.x, p1.y + add.y);
			Vertice p6 = new Vertice(p2.x + add.x, p2.y + add.y);
			
			polygon.add(p1);
			polygon.add(p5);
			polygon.add(p6);
			polygon.add(p2);
			polygon.add(p4);
			polygon.add(p3);
			area.add(new Area(polygon));
		}
		return area;
	}
	
	//retourne les projections des coins passer en paramètre
	private Polygon getDropShadows(Light light, Polygon visibleVertices) {
		//liste des distances entre la source lumineuse et les coins du mur
		ArrayList<Double> cornerDistances = this.getCornerDistances(light, visibleVertices);
				
		Polygon dropShadow = new Polygon();
		for(int i = 0; i < visibleVertices.size(); i++){
			dropShadow.add(this.getDropShadow(light, visibleVertices.get(i), cornerDistances.get(i)));
		}
		return dropShadow;
	}
	
	//retourne les projections du coins passer en paramètre
	private Vertice getDropShadow(Light light, Vertice vertice) {
		return this.getDropShadow(light, vertice, light.distance(vertice));
	}
		
	//retourne les projections du coins passer en paramètre
	private Vertice getDropShadow(Light light, Vertice vertice, double distance){
		double x = light.getX()+(vertice.getX()-light.getX())*((light.getRadius()/distance));
		double y = light.getY()+(vertice.getY()-light.getY())*((light.getRadius()/distance));
		double newDistance = light.distance(x, y);
		if(newDistance < distance){
			return new Vertice(vertice.getX(), vertice.getY());
		}
		return new Vertice(x, y);
	}
	
	//retourne la liste des distances entre la source lumineuse et les coins du mur
	private ArrayList<Double> getCornerDistances(Light light, Polygon visibleVertices) {
		ArrayList<Double> cornerDistances = new ArrayList<Double>();
		
		for(Point2D vertice : visibleVertices.getVertices()){
			cornerDistances.add(light.distance(vertice));
		}
		return cornerDistances;
	}
	
}
