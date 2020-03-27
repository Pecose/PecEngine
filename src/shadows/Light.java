package shadows;

import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Light extends Point2D.Double{
	
	private RadialGradientPaint gradient;
	private double radius;
	
	public double getRadius() { return radius; }
	public double getWidth() { return radius*2; }

	public RadialGradientPaint getGradient() { return gradient; }
	
	public Ellipse2D.Double getEllipse2D() {
		return new Ellipse2D.Double((int)getX() - getRadius(), (int)getY() - getRadius(), getWidth(), getWidth());
	}
	
	private void setGradient(double x, double y, double width, float intensity, Color color) {
		this.radius = width/2;
		this.x = x;
		this.y = y;
		
		float[] fraction = new float[] {intensity, 1f};
		Color[] colors = new Color[] {color, new Color(0, 0, 0, 0)};
		gradient = new RadialGradientPaint(new Point2D.Double(x, y), (float) this.radius, fraction, colors);
	}
	
	public Light(double x, double y, double width, float intensity, Color color) {
		this.setGradient(x, y, width, intensity, color);
	}
	
}
