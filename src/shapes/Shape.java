package shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Shape{
	protected int[] pixels;
	protected int widthM, heightM, ratio;
	protected Color color1, color2;
	protected double spread;
	public static List<Shape> list = Collections.synchronizedList(new ArrayList<Shape>());
	
	public int getWidth(){ return this.widthM; }
	public int getHeight(){ return this.heightM; }
	public int[] getPixels(){ return pixels; }
	
	public Shape(int width, int height, Color color1, Color color2, double spread){
		this.widthM = width;
		this.heightM = height;
		this.color1 = color1;
		this.color2 = color2;
		this.spread = spread;
		this.pixels = new int[width*height];
		Shape.list.add(this);
	}
	
	public void reload(int width, int height){ construct(width, height); }
	public void reload(){ construct(); }
	public abstract void construct();
	public abstract void construct(int width, int height);
}
