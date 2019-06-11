package pecose.billos.display;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Window extends Frame{
	
	private static final long serialVersionUID = 46928638469284L;
	private BufferStrategy strategy; 
	public int width = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public int height = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	protected int x = 0;
	protected int y = 0;
	private Boolean fullScreen = false;
	public static ArrayList<Window> window = new ArrayList<>();

   	protected Brush brush;
	
   	public int getWidth(){ return this.width; }
	public int getHeight(){ return this.height; }
	public int getX(){ return this.x; }
	public int getY(){ return this.y; }
	public void setWidth(int width){ this.width = width; }
	public void setHeight(int height){ this.height = height; }
	public void setX(int x){ this.x = x; }
	public void setY(int y){ this.y = y; }
	public long timer(){ return System.currentTimeMillis(); }
	
	public void setFullScreen() {
		this.fullScreen = !fullScreen;
		this.setScreen();
		this.repaint();
	}
	
	private void setScreen(){
		if(fullScreen){
			this.dispose();
			this.setUndecorated(true);
			GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			device.setFullScreenWindow(this);
		}else{
			this.dispose();
			this.setUndecorated(false);
			this.setVisible(true);
			this.setSize(width, height); //a mettre avent setLocationRelativeTo
			this.setLocationRelativeTo(null); //Centrer la fenetre
		};
	}
	
	public Window(){ start( this.getWidth(), this.getHeight(), ""); }
	public Window(int width, int height){ start(width, height, ""); }
	public Window(int width, int height, String title){ start(width, height, title); }
	
	private void start(int width, int height, String title){
		Window.window.add(this);
		this.setTitle(title);
		this.width = width;
		this.height = height;

		Listener listener = new Listener(this);
		this.addWindowListener(listener);
		this.addMouseListener(listener);
		this.addMouseWheelListener(listener);
		this.addMouseMotionListener(listener);
		this.addKeyListener(listener);
		this.addComponentListener(listener);
		
		Starter.pecEngine.Creation(this);
		this.setScreen();
		this.setIgnoreRepaint(true);
		this.createBufferStrategy(2);
		this.strategy = this.getBufferStrategy(); 
		
		RenderingThread renderingThread = new RenderingThread();
		renderingThread.start(); 
	}
	
	class RenderingThread extends Thread{   
		public void run(){
			while(true){
				try{
					brush = new Brush((Graphics2D)(strategy.getDrawGraphics()).create());
					Starter.pecEngine.Display(brush);
					strategy.show();
					sleep(30);
				}catch(Exception e){} 
			}
		}
	}
}