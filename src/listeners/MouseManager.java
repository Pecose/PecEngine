package listeners;

import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.function.BiConsumer;

import display.Panel;

public class MouseManager {
	
	private Panel panel;
	public Panel getPanel() { return panel; }
	
	public MouseManager(Panel panel){ this.panel = panel; }
	
	public MouseEvent newEvent(MouseEvent mouseEvent) {
		Point mousePoint = mouseEvent.getPoint();
		convertMouse(mousePoint); 
		return createMouseEvent(mouseEvent, mousePoint);
	}
	
	public MouseWheelEvent newEvent(MouseWheelEvent mouseEvent) {
		Point mousePoint = mouseEvent.getPoint();
		convertMouse(mousePoint); 
		return createMouseEvent(mouseEvent, mousePoint);
	}
	
	private MouseEvent createMouseEvent(MouseEvent e, Point p) {
		return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiersEx(), p.x, p.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(),  e.getButton());
	}
	private MouseWheelEvent createMouseEvent(MouseWheelEvent e, Point p) {
		return new MouseWheelEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiersEx(), p.x, p.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(),  e.getScrollType(), e.getScrollAmount(), e.getWheelRotation(), e.getPreciseWheelRotation());
	}
	
	protected void convertMouse(Point point){
		Insets insets = getPanel().getInsets();
		AffineTransform transform = AffineTransform.getTranslateInstance(insets.left, insets.top);
		if(getPanel().getDisplayOption() == Panel.PROPORTIONAL){
			transform.concatenate(getPanel().proportionalAffineTransform(getPanel().getInnerDimension(getPanel().getDrawsize()), getPanel().getInnerDimension(getPanel().getSize())));
			
		}else if(getPanel().getDisplayOption() == Panel.ADAPTABLE){
			transform.concatenate(getPanel().affineTransform(getPanel().getInnerDimension(getPanel().getDrawsize()), getPanel().getInnerDimension(getPanel().getSize())));
		}
		try {
			transform.inverseTransform(point, point);
		} catch (Exception e) { e.printStackTrace(); } 
	}
	
	// Les propagate des MouseListener------------------------------------------------------------
	
	protected void propagate(MouseEvent mouseEvent, MouseListener[] listeners, BiConsumer<MouseListener, MouseEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	protected void propagate(MouseEvent mouseEvent, MouseMotionListener[] listeners, BiConsumer<MouseMotionListener, MouseEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	protected void propagate(MouseEvent mouseEvent, MouseClickedListener[] listeners, BiConsumer<MouseClickedListener, MouseEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	protected void propagate(MouseEvent mouseEvent, MousePressedListener[] listeners, BiConsumer<MousePressedListener, MouseEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	protected void propagate(MouseEvent mouseEvent, MouseReleasedListener[] listeners, BiConsumer<MouseReleasedListener, MouseEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	protected void propagate(MouseEvent mouseEvent, MouseEnteredListener[] listeners, BiConsumer<MouseEnteredListener, MouseEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	protected void propagate(MouseEvent mouseEvent, MouseExitedListener[] listeners, BiConsumer<MouseExitedListener, MouseEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	protected void propagate(MouseEvent mouseEvent, MouseMovedListener[] listeners, BiConsumer<MouseMovedListener, MouseEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	protected void propagate(MouseEvent mouseEvent, MouseDraggedListener[] listeners, BiConsumer<MouseDraggedListener, MouseEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	// Les propagate des MouseWheelListener------------------------------------------------------------
	
	protected void propagate(MouseWheelEvent mouseEvent, MouseWheelListener[] listeners, BiConsumer<MouseWheelListener, MouseWheelEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	protected void propagate(MouseWheelEvent mouseEvent, MouseWheelMovedListener[] listeners, BiConsumer<MouseWheelMovedListener, MouseWheelEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener, mouseEvent));
		}
	}
	
	// Les propagate des KeyListener------------------------------------------------------------
	
	protected void propagate(KeyEvent keyEvent, KeyListener[] listeners, BiConsumer<KeyListener, KeyEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,keyEvent));
		}
	}
	
	protected void propagate(KeyEvent keyEvent, KeyTypedListener[] listeners, BiConsumer<KeyTypedListener, KeyEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,keyEvent));
		}
	}
	
	protected void propagate(KeyEvent keyEvent, KeyPressedListener[] listeners, BiConsumer<KeyPressedListener, KeyEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,keyEvent));
		}
	}
	
	protected void propagate(KeyEvent keyEvent, KeyReleasedListener[] listeners, BiConsumer<KeyReleasedListener, KeyEvent> distatcher) {
		synchronized (getPanel()) {
			Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,keyEvent));
		}
	}
}
