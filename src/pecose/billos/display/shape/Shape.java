package pecose.billos.display.shape;

import java.awt.Color;
import java.util.ArrayList;

import pecose.billos.display.Window;

public abstract class Shape{
	protected int[] pixels;
	protected int widthM, heightM, ratio;
	protected Color color1, color2;
	protected double spread;
	public static ArrayList<Shape> list = new ArrayList<Shape>();
	
	public int getWidth(){ return Window.getRelative(this.widthM); }
	public int getHeight(){ return Window.getRelative(this.heightM); }
	public int[] getPixels(){ return pixels; }
	
	public Shape(int width, int height, Color color1, Color color2, double spread){
		this.widthM = width;
		this.heightM = height;
		this.color1 = color1;
		this.color2 = color2;
		this.spread = spread;
		this.pixels = new int[Window.getRelative(width)*Window.getRelative(height)];
		Shape.list.add(this);
	}
	
	public void reload(int width, int height){ construct(width, height); }
	public void reload(){ construct(); }
	public abstract void construct();
	public abstract void construct(int width, int height);
}
