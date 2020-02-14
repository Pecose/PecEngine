package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Map.Entry;

import display.Frame;
import display.Panel;
import display.PecEngine;
import listeners.MouseMovedListener;
import shadows.Light;
import shadows.Twilight;
import shadows.Wall;
import shadows.Walls;
import tools.Files;

public class Main implements PecEngine, MouseMovedListener{
	public static void main(String[] args) { PecEngine.start(new Main()); }
	
	BufferedImage foret = Files.loadBufferedImage("foret.jpg");
	
	Point2D mouse = new Point2D.Double(0,0);
	@Override
	public void creation(Frame f) {
		f.setSize(900, 500);
		f.addMouseMovedListener(this);
	}
	
	@Override
	public void display(Panel p, Graphics2D g) {
		g.drawImage(foret, null, 0, 0);
		
		Twilight twilight = new Twilight();
		
		twilight.addWall("wall1", new Wall(100, 100, 300, 10));
		twilight.addWall("wall2", new Wall(100, 110, 10, 100));
		
		twilight.addLight("fixLight", new Light(550, 550, 600, 0f, Color.red));
		twilight.addLight("mouseLight", new Light(mouse.getX(), mouse.getY(), 600, 0.5f, Color.green));
		
		twilight.displayLights();

		for(Entry<String, Wall> wall : twilight.getWallsSet()){
			g.setColor(Color.GREEN);
			g.fill(wall.getValue());
		}
		
		g.setColor(Color.green);
		g.drawImage(twilight, null, 0, 0);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mouse.setLocation(e.getX(), e.getY());
	}

}

