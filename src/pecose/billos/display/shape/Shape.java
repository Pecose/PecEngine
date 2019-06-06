package pecose.billos.display.shape;

import java.awt.Color;

import pecose.billos.display.Window;

public abstract class Shape{
	protected int[] pixels;
	protected int width, height;
	
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public int[] getPixels(){ return pixels; }
	
	protected int getRelative(double x){ return (int)(Window.window.get(0).width * x )/100; }
	
	public Shape(int width, int height){
		this.width = getRelative(width);
		this.height = getRelative(height);
		this.pixels = new int[this.width*this.height];
	}
	
	public abstract void construct(Color color);
	public abstract void construct(Color color, Color color2);
	public abstract void construct(Color color, Color color2, double dif);
}
