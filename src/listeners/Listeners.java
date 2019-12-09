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
		e = newEvent(e);
		propagate(e, getPanel().getMouseListeners(), MouseListener::mouseClicked);
		propagate(e, getPanel().getMouseClickedListeners(), MouseClickedListener::mouseClicked);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		e = newEvent(e);
		propagate(e, getPanel().getMouseListeners(), MouseListener::mousePressed);
		propagate(e, getPanel().getMousePressedListeners(), MousePressedListener::mousePressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		e = newEvent(e);
		propagate(e, getPanel().getMouseListeners(), MouseListener::mouseReleased);
		propagate(e, getPanel().getMouseReleasedListeners(), MouseReleasedListener::mouseReleased);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		e = newEvent(e);
		propagate(e, getPanel().getMouseListeners(), MouseListener::mouseEntered);
		propagate(e, getPanel().getMouseEnteredListeners(), MouseEnteredListener::mouseEntered);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		e = newEvent(e);
		propagate(e, getPanel().getMouseListeners(), MouseListener::mouseExited);
		propagate(e, getPanel().getMouseExitedListeners(), MouseExitedListener::mouseExited);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		e = newEvent(e);
		propagate(e, getPanel().getMouseMotionListeners(), MouseMotionListener::mouseMoved);
		propagate(e, getPanel().getMouseMovedListeners(), MouseMovedListener::mouseMoved);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		e = newEvent(e);
		propagate(e, getPanel().getMouseMotionListeners(), MouseMotionListener::mouseDragged);
		propagate(e, getPanel().getMouseDraggedListeners(), MouseDraggedListener::mouseDragged);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		e = newEvent(e);
		propagate(e, getPanel().getMouseWheelListeners(), MouseWheelListener::mouseWheelMoved);
		propagate(e, getPanel().getMouseWheelMovedListeners(), MouseWheelMovedListener::mouseWheelMoved);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		propagate(e, getPanel().getKeyListeners(), KeyListener::keyTyped);
		propagate(e, getPanel().getKeyTypedListeners(), KeyTypedListener::keyTyped);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		propagate(e, getPanel().getKeyListeners(), KeyListener::keyPressed);
		propagate(e, getPanel().getKeyPressedListeners(), KeyPressedListener::keyPressed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		propagate(e, getPanel().getKeyListeners(), KeyListener::keyReleased);
		propagate(e, getPanel().getKeyReleasedListeners(), KeyReleasedListener::keyReleased);
	}
}
