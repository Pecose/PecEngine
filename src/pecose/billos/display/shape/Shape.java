package pecose.billos.display.shape;

import java.awt.Color;

import pecose.billos.display.Window;

public abstract class Shape extends Shapes{
	protected int[] pixels;
	protected int width, height;
	
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public int[] getPixels(){ return pixels; }
	
	public Shape(int width, int height){
		this.width = Window.getRelative(width);
		this.height = Window.getRelative(height);
		this.pixels = new int[this.width*this.height];
	}
	
	public abstract void construct(Color color);
	public abstract void construct(Color color, Color color2);
	public abstract void construct(Color color, Color color2, double dif);
}
