package pecose.billos.display;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import pecose.billos.display.shape.Shape;

public class Brush {
	public static Graphics2D graphics;
	public Brush(Graphics2D graphics) { Brush.graphics = graphics; }
	
	
	public void drawShape(Shape ellipse, Point2D position){ this.drawShape(ellipse, position.getX(), position.getY()); }
	public void drawShape(Shape ellipse, double posX, double posY){
		BufferedImage image = this.ColorArrayToBufferedImage(ellipse.getPixels(), ellipse.getWidth(), ellipse.getHeight());
		graphics.drawImage(image, null, Window.getRelative(posX)-ellipse.getWidth()/2, 
										Window.getRelative(posY)-ellipse.getHeight()/2);
	}
	
	public void reset(){
		graphics.clearRect(0, 0, Window.window.getWidth(), Window.window.getHeight());
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
