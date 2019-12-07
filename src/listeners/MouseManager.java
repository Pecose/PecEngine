package listeners;

import java.awt.Dimension;
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
import java.awt.geom.NoninvertibleTransformException;
import java.util.Arrays;
import java.util.function.BiConsumer;

import display.Panel;

public class MouseManager {
	
	private Panel panel;
	public Panel getPanel() { return panel; }
	
	public MouseManager(Panel panel){ this.panel = panel; }
	
	protected void propagate(MouseEvent mouseEvent, MouseListener[] listeners, BiConsumer<MouseListener, MouseEvent> distatcher) {
		Point mousePoint = mouseEvent.getPoint();
		try {
			convertMouse(mousePoint); 
			MouseEvent newEvent = createMouseEvent(mouseEvent, mousePoint);
			synchronized (getPanel()) {
				Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,newEvent));
			}
		}catch(Exception e){ e.printStackTrace(); }
	}
	protected void propagate(MouseEvent mouseEvent, MouseMotionListener[] listeners, BiConsumer<MouseMotionListener, MouseEvent> distatcher) {
		Point mousePoint = mouseEvent.getPoint();
		try {
			convertMouse(mousePoint); 
			MouseEvent newEvent = createMouseEvent(mouseEvent, mousePoint);
			synchronized (getPanel()) {
				Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,newEvent));
			}
		}catch(Exception e){ e.printStackTrace(); }
	}
	protected void propagate(MouseWheelEvent mouseEvent, MouseWheelListener[] listeners, BiConsumer<MouseWheelListener, MouseWheelEvent> distatcher) {
		Point mousePoint = mouseEvent.getPoint();
		try {
			convertMouse(mousePoint); 
			MouseWheelEvent newEvent = createMouseEvent(mouseEvent, mousePoint);
			synchronized (getPanel()) {
				Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,newEvent));
			}
		}catch(Exception e){ e.printStackTrace(); }
	}
	
	protected void propagate(KeyEvent keyEvent, KeyListener[] listeners, BiConsumer<KeyListener, KeyEvent> distatcher) {
		try {
			synchronized (getPanel()) {
				Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,keyEvent));
			}
		}catch(Exception e){ e.printStackTrace(); }
	}

	private MouseEvent createMouseEvent(MouseEvent e, Point p) {
		return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiersEx(), p.x, p.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(),  e.getButton());
	}
	private MouseWheelEvent createMouseEvent(MouseWheelEvent e, Point p) {
		return new MouseWheelEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiersEx(), p.x, p.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(),  e.getScrollType(), e.getScrollAmount(), e.getWheelRotation(), e.getPreciseWheelRotation());
	}
	
	protected void convertMouse(Point point) throws NoninvertibleTransformException {
		Insets insets = getPanel().getInsets();
		AffineTransform transform = AffineTransform.getTranslateInstance(insets.left, insets.top);
		if ( getPanel().isAdaptable() ) {
			transform.concatenate(proportionalAffineTransform(getPanel().getInnerDimension(getPanel().getDrawsize()), getPanel().getInnerDimension(getPanel().getPreferredSize())));
			if ( getPanel().isProportional() ) {
				transform.concatenate(proportionalAffineTransform(getPanel().getInnerDimension(getPanel().getDrawsize()), getPanel().getInnerDimension(getPanel().getSize())));
			}
			else {
				transform.concatenate(affineTransform(getPanel().getInnerDimension(getPanel().getDrawsize()), getPanel().getInnerDimension(getPanel().getSize())));
			}
		}  
		transform.inverseTransform(point, point); 
	}
	
	private AffineTransform affineTransform(Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth();
		double sy = tosize.getHeight()/fromsize.getHeight();
		return AffineTransform.getScaleInstance(sx, sy);
	}
	
	private AffineTransform proportionalAffineTransform(Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth();
		double height = fromsize.getHeight()*sx;
		if ( height > tosize.getHeight() ) {
			sx = tosize.getHeight()/fromsize.getHeight(); 
		}
		return AffineTransform.getScaleInstance(sx, sx);
	}

}
