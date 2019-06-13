package pecose.billos.display;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import pecose.billos.display.shape.Shape;

public class Listener implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener, WindowListener, ComponentListener{

	@Override
	public void keyPressed(KeyEvent e){ Starter.pecEngine.Keyboard(e, null, null); }

	@Override
	public void keyReleased(KeyEvent e){ Starter.pecEngine.Keyboard(null, e, null); }
	
	@Override
	public void keyTyped(KeyEvent e){ Starter.pecEngine.Keyboard(null, null, e); }

	@Override
	public void mousePressed(MouseEvent e){ Starter.pecEngine.Mouse(e, null, null, null); }

	@Override
	public void mouseReleased(MouseEvent e){ Starter.pecEngine.Mouse(null, e, null, null); }

	@Override
	public void mouseClicked(MouseEvent e){ Starter.pecEngine.Mouse(null, null, e, null); }

	@Override
	public void mouseWheelMoved(MouseWheelEvent e){ Starter.pecEngine.Mouse(null, null, null, e); }

	@Override
	public void mouseMoved(MouseEvent e){ Starter.pecEngine.Screen(e, null, null, null); }
	
	@Override
	public void mouseEntered(MouseEvent e){ Starter.pecEngine.Screen(null, e, null, null); }

	@Override
	public void mouseExited(MouseEvent e){ Starter.pecEngine.Screen(null, null, e, null); }
	
	@Override
	public void mouseDragged(MouseEvent e){ Starter.pecEngine.Screen(null, null, null, e); }

	@Override
	public void windowClosing(WindowEvent e) { 
		if(Starter.addListener != null){
			Starter.addListener.Window(e, null, null);
		} 
		System.exit(0);
	}
	
	@Override
	public void windowOpened(WindowEvent e) { 
		if(Starter.addListener != null){
			Starter.addListener.Window(null, e, null);
		}  
	}

	@Override
	public void windowClosed(WindowEvent e) {
		if(Starter.addListener != null){
			Starter.addListener.Window(null, null, e);
		}
	}

	@Override
	public void windowIconified(WindowEvent e) {
		if(Starter.addListener != null){
			Starter.addListener.Taskbar(e, null);
		}
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		if(Starter.addListener != null){
			Starter.addListener.Taskbar(null, e);
		}
	}

	@Override
	public void windowActivated(WindowEvent e) {
		if(Starter.addListener != null){
			Starter.addListener.Desktop(e, null);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		if(Starter.addListener != null){
			Starter.addListener.Desktop(null, e);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Window.setWidth(e.getComponent().getSize().width);
		Window.setHeight(e.getComponent().getSize().height);
		for(Shape shape : Shape.list){ shape.reload(); }
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		Window.setX((int)e.getComponent().getLocationOnScreen().getX());
		Window.setY((int)e.getComponent().getLocationOnScreen().getY());
	}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}
	
}