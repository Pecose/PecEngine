package shadows;

import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

public class Light {
	
	private RadialGradientPaint gradent;
	private int x, y, width;
	
	public int getWidth() { return width; }
	public int getY() { return y - (width/2); }
	public int getX() { return x - (width/2); }

	public RadialGradientPaint getGradent() { return gradent; }
	
	private void setGradient(double x, double y, int width, float intensity, Color color) {
		this.x = (int) x;
		this.y = (int) y;
		this.width = width;
		Point2D center = new Point2D.Double(x, y);
		float[] fraction = new float[] {intensity, 1f};
		Color[] colors = new Color[] {color, new Color(0, 0, 0, 0)};
		gradent = new RadialGradientPaint(center, width/2, fraction, colors);
	}
	
	public Light(double x, double y, int width, float intensity, Color color) {
		this.setGradient(x, y, width, intensity, color);
	}
}
