package shadows;

import java.awt.Color;
import java.util.ArrayList;

public class Lights extends ArrayList<Light>{
	public boolean add(double x, double y, int width, float intensity, Color color) {
		return this.add(new Light(x, y, width, intensity, color));
	}
}
