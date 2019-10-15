package display;

import java.awt.Canvas;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{
	
	private static final long serialVersionUID = 46928638469284L;
//	protected Brush brush;
	protected Canvas canvas = new Canvas();
	protected JPanel panel;
	protected PecEngine pecEngine;
	
	
	public Canvas getCanvas(){ return this.canvas; }
	public int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	protected static int x = 0;
	protected static int y = 0;
	private static Boolean fullScreen = false;
	

	public Window(PecEngine pecEngine, JPanel panel){ 
		this.panel = panel;
		this.pecEngine = pecEngine;
		this.start(); 
	}
	public void setWidth(int width){ this.width = width; }
	public void setHeight(int height){ this.height = height; }
	public void setX(int x){ Window.x = x; }
	public void setY(int y){ Window.y = y; }
	public long timer(){ return System.currentTimeMillis(); }
	
	public int getRelative(double x){ return (int)( width * x )/100; }
	
	public void setFullScreen() {
		fullScreen = !fullScreen;
		setScreen();
		repaint();
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
	
	public void start(){
		panel.add(canvas);
		this.add(panel);
		this.addWindowListener(pecEngine);
		this.addMouseListener(pecEngine);
		this.addMouseWheelListener(pecEngine);
		this.addMouseMotionListener(pecEngine);
		this.addKeyListener(pecEngine);
		this.addComponentListener(pecEngine);
		this.setScreen();
		this.setIgnoreRepaint(true);
		this.pecEngine.Creation(this); 
	}
	
}