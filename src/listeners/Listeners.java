package listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import display.Panel;

public class Listeners extends MouseManager implements MouseInputListener, MouseWheelListener, KeyListener{

	public Listeners(Panel panel){ super(panel); }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		propagate(e, getPanel().getMouseListeners(), MouseListener::mouseClicked);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		propagate(e, getPanel().getMouseListeners(), MouseListener::mousePressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		propagate(e, getPanel().getMouseListeners(), MouseListener::mousePressed);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		propagate(e, getPanel().getMouseListeners(), MouseListener::mouseEntered);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		propagate(e, getPanel().getMouseListeners(), MouseListener::mouseExited);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		propagate(e, getPanel().getMouseMotionListeners(), MouseMotionListener::mouseMoved);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		propagate(e, getPanel().getMouseMotionListeners(), MouseMotionListener::mouseDragged);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		propagate(e, getPanel().getMouseWheelListeners(), MouseWheelListener::mouseWheelMoved);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		propagate(e, getPanel().getKeyListeners(), KeyListener::keyTyped);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		propagate(e, getPanel().getKeyListeners(), KeyListener::keyPressed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		propagate(e, getPanel().getKeyListeners(), KeyListener::keyReleased);
	}
}
