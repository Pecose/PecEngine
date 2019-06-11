package pecose.billos.display.shape;

import java.awt.Color;

import pecose.billos.display.Window;

public class Ellipse extends Shape{
	
	private int widthR, heightR, ratio;
	private Color color1, color2;

	public Ellipse(int width, int height, Color color){
		super(width, height);
		construct(color);
	}
	public Ellipse(int width, int height, Color color, Color color2){
		super(width, height);
		construct(color, color2);
	}
	
	public Ellipse(int width, int height, Color color, Color color2, double dif){
		super(width, height);
		construct(color, color2, Window.getRelative(dif)/2);
	}
	
	@Override
	public void construct(Color color) {
		int[][] pixelsBuffer = new int[width][height];
		this.widthR = width/2;
		this.heightR = height/2;
		for(int x = widthR-1; x > 0; x--){
			double y = heightR * Math.sqrt(1 - Math.pow((double)x/widthR, 2));
			for(int h = heightR; h >= heightR-y; h--){
				pixelsBuffer[widthR-x][h] = color.getRGB();
				pixelsBuffer[widthR-x][height-h] = color.getRGB();
				
				pixelsBuffer[widthR+x][h] = color.getRGB();
				pixelsBuffer[widthR+x][height-h] = color.getRGB();
				
				pixelsBuffer[widthR][h] = color.getRGB();
				pixelsBuffer[widthR][height-h] = color.getRGB();
			}
		}
		
		for(int y = 0, i = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				pixels[i++] = pixelsBuffer[x][y];
			}
		}
	}

	@Override
	public void construct(Color color1, Color color2) {
		int[][] pixelsBuffer = new int[width][height];
		this.widthR = width/2;
		this.heightR = height/2;
		this.color1 = color1;
		this.color2 = color2;
		
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
		for(int y = 0, i = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				pixels[i++] = pixelsBuffer[x][y];
			}
		}
	}
	@Override
	public void construct(Color color1, Color color2, double dif) {
		int[][] pixelsBuffer = new int[width][height];
		this.widthR = width/2;
		this.heightR = height/2;
		this.color1 = color1;
		this.color2 = color2;
		
		if(width >= height){
			if(dif == 0){ dif = width; }
			for(int x = widthR-1; x > 0; x--){
				double y = heightR * Math.sqrt(1 - Math.pow((double)x/widthR, 2));
				for(int h = heightR; h >= heightR-y; h--){
					this.ratio = (int) (h * widthR / heightR);
							
					int color = this.calculeColor(widthR-x, widthR, dif);
					pixelsBuffer[widthR-x][h] = color;
					pixelsBuffer[widthR-x][height-h] = color;
					
					color = this.calculeColor(widthR+x, widthR, dif);
					pixelsBuffer[widthR+x][h] = color;
					pixelsBuffer[widthR+x][height-h] = color;
					
					color = this.calculeColor(widthR, widthR, dif);
					pixelsBuffer[widthR][h] = color;
					pixelsBuffer[widthR][height-h] = color;
				}
			}
		}else{
			if(dif == 0){ dif = height; }
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
		for(int y = 0, i = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				pixels[i++] = pixelsBuffer[x][y];
			}
		}
	}
	
	private int calculeColor(double x, int widthR){
		double distance = Math.sqrt(Math.pow((widthR - x),2) + Math.pow((widthR - ratio),2));
		double percent = distance / widthR * 100;
		if(percent > 100) percent = 100;
		return new Color((int) ((color2.getRed() * percent + color1.getRed() * (100 - percent)) / 100), 
				(int) ((color2.getGreen() * percent + color1.getGreen() * (100 - percent)) / 100), 
				(int) ((color2.getBlue() * percent + color1.getBlue() * (100 - percent)) / 100), 
				(int) ((color2.getAlpha() * percent + color1.getAlpha() * (100 - percent)) / 100)).getRGB();
	}
	
	private int calculeColor(double x, int widthR, double dif){
		double distance = Math.sqrt(Math.pow((widthR - x),2) + Math.pow((widthR - ratio),2));
		double percent = 100 - (distance-dif) / (widthR-dif) * 100;
		if(percent > 100) return color1.getRGB();
		return new Color((int) ((color1.getRed() * percent + color2.getRed() * (100 - percent)) / 100), 
				(int) ((color1.getGreen() * percent + color2.getGreen() * (100 - percent)) / 100), 
				(int) ((color1.getBlue() * percent + color2.getBlue() * (100 - percent)) / 100), 
				(int) ((color1.getAlpha() * percent + color2.getAlpha() * (100 - percent)) / 100)).getRGB();
	}
	
	
	
}
