package display;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = 46928638469284L;
	public Panel panel;
	public PecEngine pecEngine;
	
	protected static int x = 0;
	protected static int y = 0;
	private boolean fullScreen = false;
	

	public Frame(PecEngine pecEngine, Panel panel){ 
		this.panel = panel;
		this.pecEngine = pecEngine;

		this.start(); 
	}
	
	public void setX(int x){ Frame.x = x; }
	public void setY(int y){ Frame.y = y; }
	public long timer(){ return System.currentTimeMillis(); }
	
	public int getRelative(double x){ return (int)( Screen.getWidth() * x )/100; }
	
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
		this.pecEngine.creation(this);
		this.goWindowed();
	}
	
	//Liste des getters ------------------------------------------------
		@Override
		public synchronized MouseListener[] getMouseListeners() {
			return panel.getMouseListeners();
		}
	 
		@Override
		public synchronized MouseMotionListener[] getMouseMotionListeners() {
			return panel.getMouseMotionListeners();
		}
		
		@Override
		public synchronized MouseWheelListener[] getMouseWheelListeners() {
			return panel.getMouseWheelListeners();
		} 
	 
		//Liste des adders ------------------------------------------------
		@Override
		public synchronized void addMouseListener(MouseListener l) {
			panel.addMouseListener(l);
		}
		
		@Override
		public synchronized void addMouseWheelListener(MouseWheelListener l) {
			panel.addMouseWheelListener(l);
		}
	 
		@Override
		public synchronized void addMouseMotionListener(MouseMotionListener l) {
			panel.addMouseMotionListener(l);
		}
	 
		//Liste des removers ------------------------------------------------
		@Override
		public synchronized void removeMouseListener(MouseListener l) {
			panel.removeMouseListener(l);
		}

		@Override
		public synchronized void removeMouseMotionListener(MouseMotionListener l) {
			panel.removeMouseMotionListener(l);
		}
	 
		@Override
		public synchronized void removeMouseWheelListener(MouseWheelListener l) {
			panel.removeMouseWheelListener(l);
		}
	
}