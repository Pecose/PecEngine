package pecose.billos.display.shape;

import java.awt.Color;

import pecose.billos.display.Window;

public class Rectangle extends Shape{
	
	public Rectangle(int width, int height, Color color1){
		super(width, height, color1, null, 1);
		construct();
	}
	
	@Override
	public void construct() {
		construct(this.widthM, this.heightM);
	}
	
	@Override
	public void construct(int www, int hhh) {
		int width = Window.getRelative(www);
		int height = Window.getRelative(hhh);
		this.pixels = new int[width*height];
		
		for(int x = 0; x < height*width; x++){
			this.pixels[x] = color1.getRGB();
		}
		
		this.widthM = www;
		this.heightM = hhh;
	}
	
}
