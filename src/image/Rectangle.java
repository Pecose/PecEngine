package image;

import java.awt.Color;

public class Rectangle extends Image{
	
	public Rectangle(int width, int height, Color color) {
		super(width, height);
		
		for(int x = 0; x < height*width; x++) this.pixels[x] = color.getRGB();
		
	}
}
