package pecose.billos.display.shape;

import java.awt.Color;

import pecose.billos.display.Window;

public abstract class Shape extends Shapes{
	protected int[] pixels;
	protected int widthM, heightM, ratio;
	protected Color color1, color2;
	protected double spread;
	
	public int getWidth(){ return widthM; }
	public int getHeight(){ return heightM; }
	public int[] getPixels(){ return pixels; }
	
	public Shape(int width, int height, Color color1, Color color2, double spread){
		this.widthM = Window.getRelative(width);
		this.heightM = Window.getRelative(height);
		this.color1 = color1;
		this.color2 = color2;
		this.spread = spread;
		this.pixels = new int[this.widthM*this.heightM];
	}
	
	public void reload(int width, int height){
		construct(Window.getRelative(width), Window.getRelative(height));
	}
	public abstract void construct();
	public abstract void construct(int width, int height);
}
