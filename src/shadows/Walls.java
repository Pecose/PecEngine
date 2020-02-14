package shadows;

import java.util.ArrayList;

public class Walls extends ArrayList<Wall>{
	public boolean add(int x, int y, int width, int height) {
		return this.add(new Wall(x, y, width, height));
	}
}
