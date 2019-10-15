package shapes;

import java.awt.Color;

public class Rectangl extends Shape{
	
	public Rectangl(int width, int height, Color color1){
		super(width, height, color1, null, 1);
		construct();
	}
	
	@Override
	public void construct() {
		construct(this.widthM, this.heightM);
	}
	
	@Override
	public void construct(int width, int height) {
		this.pixels = new int[width*height];
		
		for(int x = 0; x < height*width; x++){
			this.pixels[x] = color1.getRGB();
		}
		
		this.widthM = width;
		this.heightM = height;
	}
	
}
