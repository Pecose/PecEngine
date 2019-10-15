package image;

public class Image {

	public int[] pixels;
	public int width, height;
	
	public Image(int width, int height){
		this.width = width;
		this.height = height;
		this.pixels = new int[width*height];
	}
	
	
}
