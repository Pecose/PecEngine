package image;

import java.awt.geom.Point2D;

public class Image {

	protected int[] pixels;
	protected int width, height;
	
	public int[] getPixels(){ return this.pixels; }
	public int getWidth(){ return this.width; }
	public int getHeight(){return this.height; }
	
	public Image(int width, int height){
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
	}
	
	public void add(Image image, Point2D position){
		this.add(image, (int)position.getX(), (int)position.getY());
	}
	
	public void add(Image image, int x, int y){
		
	}
	
}
