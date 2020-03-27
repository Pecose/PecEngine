package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import display.Frame;
import display.Panel;
import display.PecEngine;
import listeners.MouseMovedListener;
import shadows.Light;
import shadows.Use;
import shadows.Wall;
import shadows.World;
import tools.Files;

public class Main implements PecEngine, MouseMovedListener{
	public static void main(String[] args) { PecEngine.start(new Main()); }
	
	BufferedImage foret = Files.loadBufferedImage("foret.jpg");
	
	@Override
	public void creation(Frame f) {
		f.setSize(900, 500);
		f.addMouseMovedListener(this);
	}
	
	@Override
	public void display(Panel p, Graphics2D g) {
//		g.drawImage(foret, null, 0, 0);
		
		World world = new World(g);
		
		world.setTwilight(100);
		
		world.addWall("wall1", new Wall(100, 100, 300, 10));
		world.addWall("wall2", new Wall(100, 110, 10, 100));
		world.addWall("wall3", new Wall(500, 500, 10, 10));
		
		Wall wall4 = new Wall();
		wall4.addPoint(763, 446);
//		wall4.addPoint(843, 628);
		wall4.addPoint(725, 508);
		wall4.addPoint(753, 416);
		wall4.addPoint(853, 428);
		wall4.addPoint(858, 475);
		
		world.addWall("wall4", wall4);
		
//		world.addLight("fixLight2", new Light(100, 100, 600, 0.1f, Color.blue));
//		world.addLight("fixLight", new Light(550, 550, 600, 0.1f, Color.red));
		world.addLight("mouseLight", new Light(Use.mouse.getX(), Use.mouse.getY(), 600, 0.5f, Color.green));
		
		world.displayLights();

	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		Use.mouse.setLocation(e.getX(), e.getY());
//		System.out.println(e.getX()+ " " +e.getY());
	}

}

