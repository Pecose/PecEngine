package shadows;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import display.Screen;

public class Twilight extends BufferedImage{
	
	private Graphics2D graphics;
	private HashMap<String, Light> lights = new HashMap<String, Light>();
	private HashMap<String, Wall> walls = new HashMap<String, Wall>();

	public void addLight(String name, Light light) { lights.put(name, light); }
	public void addWall(String name, Wall wall) { walls.put(name, wall); }
	
	public Light getLight(String name) { return lights.get(name); }
	public Wall getWall(String name) { return walls.get(name); }
	
	public void setLight(String name, Light light) { lights.replace(name, light); }
	public void setWall(String name, Wall wall) { walls.replace(name, wall); }
	
	public void supLight(String name) { lights.remove(name); }
	public void supWall(String name) { walls.remove(name); }
	
	public Set<Entry<String, Wall>> getWallsSet(){
		return walls.entrySet();
	}
	
	public Graphics2D getGraphics() { return graphics; }
	
	public Twilight() {
		super(Screen.getWidth(), Screen.getHeight(), BufferedImage.TYPE_INT_ARGB);
		this.graphics = this.createGraphics();
		this.graphics.setComposite(BlendComposite.Add);
	}

	public void displayLights() {
		for(Entry<String, Light> light : lights.entrySet()){
			this.displayLight(light.getValue());
		}this.reverseAlpha();
	}
	
	public void displayLight(Light light) {
		this.graphics.setPaint(light.getGradent());
		this.graphics.fillOval((int)light.getX(), (int)light.getY(), light.getWidth(), light.getWidth());
	}
	
	public void reverseAlpha() {
		int[] data = ((DataBufferInt)this.getAlphaRaster().getDataBuffer()).getData();
		for(int i = 0; i < data.length; i++) {
			data[i] = data[i] & 0xff000000 ^ 0xff000000 | data[i] & 0xffffff;
		}
	}
}
