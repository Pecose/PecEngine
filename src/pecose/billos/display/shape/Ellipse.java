package pecose.billos.display.shape;

import java.awt.Color;

import pecose.billos.display.Window;

public class Ellipse extends Shape{
	
	public Ellipse(int width, int height, Color color1){
		super(width, height, color1, color1, 1);
		construct();
	}
	public Ellipse(int width, int height, Color color1, Color color2){
		super(width, height, color1, color2, 1);
		construct();
	}
	
	public Ellipse(int width, int height, Color color1, Color color2, double spread){
		super(width, height, color1, color2, spread);
		construct();
	}
	
//	@Override
//	public void construct(Color color) {
//		int[][] pixelsBuffer = new int[width][height];
//		this.widthR = width/2;
//		this.heightR = height/2;
//		
//		for(int x = widthR-1; x > 0; x--){
//			double y = heightR * Math.sqrt(1 - Math.pow((double)x/widthR, 2));
//			for(int h = heightR; h >= heightR-y; h--){
//				pixelsBuffer[widthR-x][h] = color.getRGB();
//				pixelsBuffer[widthR-x][height-h] = color.getRGB();
//				
//				pixelsBuffer[widthR+x][h] = color.getRGB();
//				pixelsBuffer[widthR+x][height-h] = color.getRGB();
//				
//				pixelsBuffer[widthR][h] = color.getRGB();
//				pixelsBuffer[widthR][height-h] = color.getRGB();
//			}
//		}
//		
//		for(int y = 0, i = 0; y < height; y++){
//			for(int x = 0; x < width; x++){
//				pixels[i++] = pixelsBuffer[x][y];
//			}
//		}
//	}
	@Override
	public void construct() {
		construct(this.widthM, this.heightM);
	}
	
	@Override
	public void construct(int width, int height) {
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
		this.widthM = width;
		this.heightM = height;
	}
	
	private int calculeColor(double x, int widthR){
		double distance = Math.sqrt(Math.pow((widthR - x),2) + Math.pow((widthR - ratio),2));
		double percentPlus = 100 / spread * 100;
		double tempo = distance / widthR * percentPlus;
		double percent = percentPlus - tempo;
		if(percent > 100) percent = 100;
		if(percent < 0) percent = 0;
		return new Color((int) ((color1.getRed() * percent + color2.getRed() * (100 - percent)) / 100), 
				(int) ((color1.getGreen() * percent + color2.getGreen() * (100 - percent)) / 100), 
				(int) ((color1.getBlue() * percent + color2.getBlue() * (100 - percent)) / 100), 
				(int) ((color1.getAlpha() * percent + color2.getAlpha() * (100 - percent)) / 100)).getRGB();
	}
	
	
	
}
