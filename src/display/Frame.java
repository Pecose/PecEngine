package display;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = 46928638469284L;
	protected JPanel panel;
	protected PecEngine pecEngine;
	
	protected static int x = 0;
	protected static int y = 0;
	private boolean fullScreen = false;
	

	public Frame(PecEngine pecEngine, JPanel panel){ 
		this.panel = panel;
		this.pecEngine = pecEngine;

		this.start(); 
	}
	
	
	
	public int getScreenWidth(){ return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(); }
	public int getScreenHeight(){ return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(); }
	
	public void setX(int x){ Frame.x = x; }
	public void setY(int y){ Frame.y = y; }
	public long timer(){ return System.currentTimeMillis(); }
	
	public int getRelative(double x){ return (int)( getScreenWidth() * x )/100; }
	
	public void setFullScreen() {
		boolean old = fullScreen;
		this.fullScreen = !fullScreen;
		this.firePropertyChange("FULLSCREEN", old, fullScreen);
	}
	
	public void toggleFullScreen(PropertyChangeEvent e) {
		if((boolean) e.getNewValue()) {
			this.goFullScreen();
		}else {
			this.goWindowed();
		}
	}
	
	public void goFullScreen() {
		this.dispose();
		this.setUndecorated(true);
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		device.setFullScreenWindow(this);
	}
	
	public void goWindowed() {
		this.dispose();
		this.setUndecorated(false);
//		this.setPreferredSize(new Dimension(getScreenWidth(), getScreenHeight())); //a mettre avent setLocationRelativeTo
		this.pack();
		this.setLocationRelativeTo(null); //Centrer la fenetre
		this.setVisible(true);
	}
	
	public void start(){
		this.addPropertyChangeListener("FULLSCREEN", e->this.toggleFullScreen(e));
		this.setContentPane(panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pecEngine.creation(this, new Mouse(this));
		this.goWindowed();
	}
	
}