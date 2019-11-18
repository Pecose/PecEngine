package display;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = 46928638469284L;
//	protected Brush brush;
	protected JPanel panel;
	protected PecEngine pecEngine;
	
	public int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	protected static int x = 0;
	protected static int y = 0;
	private static Boolean fullScreen = false;
	

	public Frame(PecEngine pecEngine, JPanel panel){ 
		this.panel = panel;
		this.pecEngine = pecEngine;
		this.start(); 
	}
	public void setWidth(int width){ this.width = width; }
	public void setHeight(int height){ this.height = height; }
	public void setX(int x){ Frame.x = x; }
	public void setY(int y){ Frame.y = y; }
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
			this.setPreferredSize(new Dimension(width, height)); //a mettre avent setLocationRelativeTo
			this.pack();
			this.setLocationRelativeTo(null); //Centrer la fenetre
			this.setVisible(true);
		};
	}
	
	public void start(){
		this.setContentPane(panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Frame frame = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run(){
				frame.pecEngine.creation(frame, new Mouse(frame));
			}
		});
		this.setScreen();
	}
	
}