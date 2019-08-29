package display;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{
	
	private static final long serialVersionUID = 46928638469284L;
	protected static Brush brush;
	protected static Window window;
	protected Canvas canvas = new Canvas();
	protected Listener listener = new Listener();
	protected Panel panel = new Panel();
	
	
	public Canvas getCanvas(){ return this.canvas; }
	public static int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	protected static int x = 0;
	protected static int y = 0;
	private static Boolean fullScreen = false;
	

	public Window(){ Window.window = this; start(); }
	public static void setWidth(int width){ Window.width = width; }
	public static void setHeight(int height){ Window.height = height; }
	public static void setX(int x){ Window.x = x; }
	public static void setY(int y){ Window.y = y; }
	public static long timer(){ return System.currentTimeMillis(); }
	
	public static int getRelative(double x){ return (int)(Window.window.getWidth() * x )/100; }
	
//	public void setFullScreen(){
//        this.setTitle("titre");
//        this.setSize(500,300);
//        this.setAlwaysOnTop(true);
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        this.setVisible(true);
//    }
	
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
		panel.add(canvas);
		this.add(panel);
		this.addWindowListener(listener);
		this.addMouseListener(listener);
		this.addMouseWheelListener(listener);
		this.addMouseMotionListener(listener);
		this.addKeyListener(listener);
		this.addComponentListener(listener);
		Window.setScreen();
		this.setIgnoreRepaint(true);
		Starter.pecEngine.Creation(this); 
		
	}
	
	@SuppressWarnings("serial")
	class Panel extends JPanel{  
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			try{
				Window.brush = new Brush((Graphics2D)g);
				Starter.pecEngine.Display(brush, new Mouse(window));
				repaint();
			}catch(Exception e){} 
		}
	}
}