package display;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;

import listeners.KeyPressedListener;
import listeners.KeyReleasedListener;
import listeners.KeyTypedListener;
import listeners.MouseClickedListener;
import listeners.MouseDraggedListener;
import listeners.MouseEnteredListener;
import listeners.MouseExitedListener;
import listeners.MouseMovedListener;
import listeners.MousePressedListener;
import listeners.MouseReleasedListener;
import listeners.MouseWheelMovedListener;

public class Frame extends JFrame{
	
	private static final long serialVersionUID = 46928638469284L;
	public Panel panel;
	public PecEngine pecEngine;
	
	private boolean fullScreen = true;

	public Frame(PecEngine pecEngine, Panel panel){ 
		this.panel = panel;
		this.pecEngine = pecEngine;
		this.start(); 
	}
	
	@Override
	public void setSize(Dimension d) {
		this.setPreferredSize(d);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	@Override
	public void setSize(int width , int height) {
		this.setSize(new Dimension(width, height));
	}
	
//	public long timer(){ return System.currentTimeMillis(); }
//	public int getRelative(double x){ return (int)( Screen.getWidth() * x )/100; }
	
	public void setDisplayOption(int displayOption) { this.panel.setDisplayOption(displayOption); }
	
	public void goWindowed(){
		if(this.fullScreen){ this.toggleFullScreen(); }
	}
	
	public void goFullScreen(){
		if(!this.fullScreen){ this.toggleFullScreen(); }
	}
	
	public void toggleFullScreen() {
		boolean old = fullScreen;
		this.fullScreen = !fullScreen;
		this.firePropertyChange("FULLSCREEN", old, fullScreen);
	}
	
	private void toggleFullScreen(PropertyChangeEvent e) {
		if((boolean) e.getNewValue()) {
			this.fullScreen();
		}else {
			this.windowed();
		}
	}
	
	private void fullScreen() {
		this.dispose();
		this.setUndecorated(true);
		GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		device.setFullScreenWindow(this);
	}
	
	private void windowed() {
		this.dispose();
		this.setUndecorated(false);
		this.setSize(Screen.getWidth(), Screen.getHeight());
		this.setVisible(true);
	}
	
	private void start(){
		this.addPropertyChangeListener("FULLSCREEN", e->this.toggleFullScreen(e));
		this.setContentPane(panel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.windowed();
		this.pecEngine.creation(this);
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
	@Override
	public synchronized KeyListener[] getKeyListeners() {
		return panel.getKeyListeners();
	} 
	
	public synchronized KeyPressedListener[] getKeyPressedListeners() {
		return panel.getKeyPressedListeners();
	} 
	
	public synchronized KeyReleasedListener[] getKeyReleasedListeners() {
		return panel.getKeyReleasedListeners();
	} 
	
	public synchronized KeyTypedListener[] getKeyTypedListeners() {
		return panel.getKeyTypedListeners();
	} 
	
	public synchronized MouseClickedListener[] getMouseClickedListeners() {
		return panel.getMouseClickedListeners();
	} 
	
	public synchronized MouseDraggedListener[] getMouseDraggedListeners() {
		return panel.getMouseDraggedListeners();
	} 
	
	public synchronized MouseEnteredListener[] getMouseEnteredListeners() {
		return panel.getMouseEnteredListeners();
	} 
	
	public synchronized MouseExitedListener[] getMouseExitedListeners() {
		return panel.getMouseExitedListeners();
	} 
	
	public synchronized MouseMovedListener[] getMouseMovedListeners() {
		return panel.getMouseMovedListeners();
	} 
	
	public synchronized MousePressedListener[] getMousePressedListeners() {
		return panel.getMousePressedListeners();
	}
	
	public synchronized MouseReleasedListener[] getMouseReleasedListeners() {
		return panel.getMouseReleasedListeners();
	}
	
	public synchronized MouseWheelMovedListener[] getMouseWheelMovedListeners() {
		return panel.getMouseWheelMovedListeners();
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
	
	@Override
	public synchronized void addKeyListener(KeyListener l) {
		panel.addKeyListener(l);
	}
	
	public synchronized void addKeyPressedListener(KeyPressedListener l) {
		panel.addKeyPressedListener(l);
	}
	
	public synchronized void addKeyReleasedListener(KeyReleasedListener l) {
		panel.addKeyReleasedListener(l);
	}
	
	public synchronized void addKeyTypedListener(KeyTypedListener l) {
		panel.addKeyTypedListener(l);
	}
	
	public synchronized void addMouseClickedListener(MouseClickedListener l) {
		panel.addMouseClickedListener(l);
	}
	
	public synchronized void addMouseDraggedListener(MouseDraggedListener l) {
		panel.addMouseDraggedListener(l);
	}
	
	public synchronized void addMouseEnteredListener(MouseEnteredListener l) {
		panel.addMouseEnteredListener(l);
	}
	
	public synchronized void addMouseExitedListener(MouseExitedListener l) {
		panel.addMouseExitedListener(l);
	}
	
	public synchronized void addMouseMovedListener(MouseMovedListener l) {
		panel.addMouseMovedListener(l);
	}
	
	public synchronized void addMousePressedListener(MousePressedListener l) {
		panel.addMousePressedListener(l);
	}
	
	public synchronized void addMouseReleasedListener(MouseReleasedListener l) {
		panel.addMouseReleasedListener(l);
	}
	
	public synchronized void addMouseWheelMovedListener(MouseWheelMovedListener l) {
		panel.addMouseWheelMovedListener(l);
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
	
	@Override
	public synchronized void removeKeyListener(KeyListener l) {
		panel.removeKeyListener(l);
	}
	
	public synchronized void removeKeyPressedListener(KeyPressedListener l) {
		panel.removeKeyPressedListener(l);
	}
	
	public synchronized void removeKeyReleasedListener(KeyReleasedListener l) {
		panel.removeKeyReleasedListener(l);
	}
	
	public synchronized void removeKeyTypedListener(KeyTypedListener l) {
		panel.removeKeyTypedListener(l);
	}
	
	public synchronized void removeMouseClickedListener(MouseClickedListener l) {
		panel.removeMouseClickedListener(l);
	}
	
	public synchronized void removeMouseDraggedListener(MouseDraggedListener l) {
		panel.removeMouseDraggedListener(l);
	}
	
	public synchronized void removeMouseEnteredListener(MouseEnteredListener l) {
		panel.removeMouseEnteredListener(l);
	}
	
	public synchronized void removeMouseExitedListener(MouseExitedListener l) {
		panel.removeMouseExitedListener(l);
	}
	
	public synchronized void removeMouseMovedListener(MouseMovedListener l) {
		panel.removeMouseMovedListener(l);
	}
	
	public synchronized void removeMousePressedListener(MousePressedListener l) {
		panel.removeMousePressedListener(l);
	}
	
	public synchronized void removeMouseReleasedListener(MouseReleasedListener l) {
		panel.removeMouseReleasedListener(l);
	}
	
	public synchronized void removeMouseWheelMovedListener(MouseWheelMovedListener l) {
		panel.removeMouseWheelMovedListener(l);
	}
	
}