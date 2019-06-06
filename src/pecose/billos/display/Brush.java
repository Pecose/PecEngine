package pecose.billos.display;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import pecose.billos.display.shape.Shape;

public class Brush {
	public Graphics2D graphics;
	
	private int getRelative(double x){ return (int)(Window.window.get(0).width * x )/100; }
	
	public Brush(Graphics2D graphics) { this.graphics = graphics; }

	public void drawShape(Shape ellipse, Point2D position){ this.drawShape(ellipse, position.getX(), position.getY()); }
	public void drawShape(Shape ellipse, double posX, double posY){
		BufferedImage image = this.ColorArrayToBufferedImage(ellipse.getPixels(), ellipse.getWidth(), ellipse.getHeight());
		graphics.drawImage(image, null, this.getRelative(posX)-ellipse.getWidth()/2, 
										this.getRelative(posY)-ellipse.getHeight()/2);
	}
	
	public void reset(){
		graphics.clearRect(0, 0, Window.window.get(0).getWidth(), Window.window.get(0).getHeight());
	}
	
	public int[] BufferedImageToColorArray(BufferedImage bufferedImage) {
		return ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
	}
	
	public BufferedImage ColorArrayToBufferedImage(int[] pixels, int width, int height) {
		BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		bufferedImage.getRaster().setDataElements(0, 0, width, height, pixels);
		return bufferedImage;
	}

}
