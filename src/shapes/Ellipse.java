package shapes;

import java.awt.Color;

import display.Window;

public class Ellipse extends Shape{
	
	public Ellipse(int width, int height, Color color1){
		super(width, height, color1, null, 1);
		construct();
	}
	
	public Ellipse(int width, int height, Color color1, double spread){
		super(width, height, color1, null, spread);
		construct();
	}
	
	@Override
	public void construct() {
		construct(this.widthM, this.heightM);
	}
	
	@Override
	public void construct(int www, int hhh) {
		int width = Window.getRelative(www);
		int height = Window.getRelative(hhh);
		int[][] pixelsBuffer = new int[width][height];
		int widthR = width / 2;
		int heightR = height / 2;
		if(width >= height){
			for(int x = widthR-1; x > 0; x--){
				double y = heightR * Math.sqrt(1 - Math.pow((double)x/widthR, 2));
				for(int h = heightR; h >= heightR-y; h--){
					this.ratio = (int) (h * widthR / heightR);
							
					int color = this.calculeColor(widthR-x, widthR);
					pixelsBuffer[widthR-x][h] = color;
					pixelsBuffer[widthR-x][height-h] = color;
					
					color = this.calculeColor(widthR+x, widthR);
					pixelsBuffer[widthR+x][h] = color;
					pixelsBuffer[widthR+x][height-h] = color;
					
					color = this.calculeColor(widthR, widthR);
					pixelsBuffer[widthR][h] = color;
					pixelsBuffer[widthR][height-h] = color;
				}
			}
		}else{
			for(int x = heightR-1; x > 0; x--){
				double y = widthR * Math.sqrt(1 - Math.pow((double)x/heightR, 2));
				for(int w = widthR; w >= widthR-y; w--){
					this.ratio = (int) (w * heightR / widthR);
							
					int color = this.calculeColor(heightR-x, heightR);
					pixelsBuffer[w][heightR-x] = color;
					pixelsBuffer[width-w][heightR-x] = color;
					
					color = this.calculeColor(heightR+x, heightR);
					pixelsBuffer[w][heightR+x] = color;
					pixelsBuffer[width-w][heightR+x] = color;
					
					color = this.calculeColor(heightR, heightR);
					pixelsBuffer[w][heightR] = color;
					pixelsBuffer[width-w][heightR] = color;
				}
			}
		}
		int[] tempoPixels = new int[width*height];
		for(int y = 0, i = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tempoPixels[i++] = pixelsBuffer[x][y];
			}
		}
		this.pixels = tempoPixels;
		this.widthM = www;
		this.heightM = hhh;
	}
	
	private int calculeColor(double x, int widthR){
		double distance = Math.sqrt(Math.pow((widthR - x),2) + Math.pow((widthR - ratio),2));
		double percentPlus = 100 / spread * 100;
		double tempo = distance / widthR * percentPlus;
		double percent = percentPlus - tempo;
		if(percent > 100) return 0;
		
		return Color.red.getRGB();
	}
	
//	private int calculeColor(double x, int heightR){
//		double distance = Math.sqrt(Math.pow((heightR - x),2) + Math.pow((heightR - ratio),2));
//		if(distance > spread){return color1.getRGB();}
//		return 0;
//	}
	
}
