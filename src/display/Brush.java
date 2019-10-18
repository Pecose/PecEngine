package display;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import image.Image;
import shapes.Shape;

public class Brush {
	public Graphics2D graphics;
	public Window window;
	
	public Brush(Graphics2D graphics, Window window) { 
		this.graphics = graphics; 
		this.window = window;
	}
	
	public void drawShape(Shape shape, Point2D position){ this.drawShape(shape, position.getX(), position.getY()); }
	public void drawShape(Shape shape, double posX, double posY){
		graphics.drawImage(
				this.ColorArrayToBufferedImage(shape.getPixels(), shape.getWidth(), shape.getHeight()), 
				null, 
				this.window.getRelative(posX) - shape.getWidth() / 2, 
				this.window.getRelative(posY) - shape.getHeight() / 2
		);
	}
	
	public void drawImage(Image shape, Point2D position){ this.drawImage(shape, position.getX(), position.getY()); }
	public void drawImage(Image shape, double posX, double posY){
		graphics.drawImage(
				this.ColorArrayToBufferedImage(shape.getPixels(), shape.getWidth(), shape.getHeight()), 
				null, 
				this.window.getRelative(posX) - shape.getWidth() / 2, 
				this.window.getRelative(posY) - shape.getHeight() / 2
		);
	}
	
	public void reset(){
		graphics.clearRect(0, 0, this.window.getWidth(), this.window.getHeight());
	}
	
	
	public int[] BufferedImageToColorArray(BufferedImage bufferedImage) {
		return ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
	}
	
	public BufferedImage ColorArrayToBufferedImage(int[] pixels, double width, double height) {
		BufferedImage bufferedImage = new BufferedImage((int)width,(int)height,BufferedImage.TYPE_INT_ARGB);
		bufferedImage.getRaster().setDataElements(0, 0, (int)width, (int)height, pixels);
		return bufferedImage;
	}

}
