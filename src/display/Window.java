package display;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

public class Window extends Frame{
	
	private static final long serialVersionUID = 46928638469284L;
	private static BufferStrategy strategy; 
	protected static Brush brush;
	
	public static int width = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int height = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	protected static int x = 0;
	protected static int y = 0;
	private static Boolean fullScreen = false;
	protected static Window window;

	public Window(){ Window.window = this; start(); }
	public static void setWidth(int width){ Window.width = width; }
	public static void setHeight(int height){ Window.height = height; }
	public static void setX(int x){ Window.x = x; }
	public static void setY(int y){ Window.y = y; }
	public static long timer(){ return System.currentTimeMillis(); }
	
	public static int getRelative(double x){ return (int)(Window.window.getWidth() * x )/100; }
	
	public void setFullScreen() {
		Window.fullScreen = !fullScreen;
		Window.setScreen();
		Window.window.repaint();
	}
	
	private static void setScreen(){
		if(fullScreen){
			Window.window.dispose();
			Window.window.setUndecorated(true);
			GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			device.setFullScreenWindow(Window.window);
		}else{
			Window.window.dispose();
			Window.window.setUndecorated(false);
			Window.window.setVisible(true);
			Window.window.setSize(width, height); //a mettre avent setLocationRelativeTo
			Window.window.setLocationRelativeTo(null); //Centrer la fenetre
		};
	}
	
	
	
	private void start(){
		Listener listener = new Listener();
		this.addWindowListener(listener);
		this.addMouseListener(listener);
		this.addMouseWheelListener(listener);
		this.addMouseMotionListener(listener);
		this.addKeyListener(listener);
		this.addComponentListener(listener);
		Window.setScreen();
		this.setIgnoreRepaint(true);
		this.createBufferStrategy(2);
		Window.strategy = this.getBufferStrategy();
		Window.brush = new Brush((Graphics2D)(strategy.getDrawGraphics()).create());
		Starter.pecEngine.Creation(this); 
		
		RenderingThread renderingThread = new RenderingThread();
		renderingThread.start(); 
	}
	
	class RenderingThread extends Thread{   
		public void run(){
			while(true){
				try{
					brush.reset();
					Starter.pecEngine.Display(brush, new Mouse(window));
					Window.strategy.show();
					sleep(30);
				}catch(Exception e){} 
			}
		}
	}
}