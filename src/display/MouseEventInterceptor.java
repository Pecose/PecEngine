package display;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.Arrays;
import java.util.function.BiConsumer;

public class MouseEventInterceptor extends MouseAdapter{
	
	private Panel panel;
	public MouseEventInterceptor(Panel panel){
		this.panel = panel;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		propagate(e, panel.getMouseListeners(), MouseListener::mouseClicked);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		propagate(e, panel.getMouseListeners(), MouseListener::mousePressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		propagate(e, panel.getMouseListeners(), MouseListener::mousePressed);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		propagate(e, panel.getMouseListeners(), MouseListener::mouseEntered);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		propagate(e, panel.getMouseListeners(), MouseListener::mouseExited);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		propagate(e, panel.getMouseMotionListeners(), MouseMotionListener::mouseMoved);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		propagate(e, panel.getMouseMotionListeners(), MouseMotionListener::mouseDragged);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		propagate(e, panel.getMouseWheelListeners(), MouseWheelListener::mouseWheelMoved);
	}

	private void propagate(MouseEvent mouseEvent, MouseListener[] listeners, BiConsumer<MouseListener, MouseEvent> distatcher) {
		Point mousePoint = mouseEvent.getPoint();
		try {
			convertMouse(mousePoint); 
			MouseEvent newEvent = createMouseEvent(mouseEvent, mousePoint);
			synchronized (panel) {
				Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,newEvent));
			}
		} catch (NoninvertibleTransformException e1) {
			e1.printStackTrace();
		}
	}
	private void propagate(MouseEvent mouseEvent, MouseMotionListener[] listeners, BiConsumer<MouseMotionListener, MouseEvent> distatcher) {
		Point mousePoint = mouseEvent.getPoint();
		try {
			convertMouse(mousePoint); 
			MouseEvent newEvent = createMouseEvent(mouseEvent, mousePoint);
			synchronized (panel) {
				Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,newEvent));
			}
		} catch (NoninvertibleTransformException e1) {
			e1.printStackTrace();
		}
	}
	private void propagate(MouseWheelEvent mouseEvent, MouseWheelListener[] listeners, BiConsumer<MouseWheelListener, MouseWheelEvent> distatcher) {
		Point mousePoint = mouseEvent.getPoint();
		try {
			convertMouse(mousePoint); 
			MouseWheelEvent newEvent = createMouseEvent(mouseEvent, mousePoint);
			synchronized (panel) {
				Arrays.stream(listeners).forEach(listener->distatcher.accept(listener,newEvent));
			}
		} catch (NoninvertibleTransformException e1) {
			e1.printStackTrace();
		}
	}

	private MouseEvent createMouseEvent(MouseEvent e, Point p) {
		return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(), p.x, p.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(),  e.getButton());
	}
	private MouseWheelEvent createMouseEvent(MouseWheelEvent e, Point p) {
		return new MouseWheelEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(), p.x, p.y, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(),  e.getScrollType(), e.getScrollAmount(), e.getWheelRotation(), e.getPreciseWheelRotation());
	}
	
	protected void convertMouse(Point point) throws NoninvertibleTransformException {
		Insets insets = panel.getInsets();
		AffineTransform transform = AffineTransform.getTranslateInstance(insets.left, insets.top);
		if ( panel.isAdaptable() ) {
			transform.concatenate(proportionalAffineTransform(panel.getInnerDimension(panel.getDrawsize()), panel.getInnerDimension(panel.getPreferredSize())));
			if ( panel.isProportional() ) {
				transform.concatenate(proportionalAffineTransform(panel.getInnerDimension(panel.getDrawsize()), panel.getInnerDimension(panel.getSize())));
			}
			else {
				transform.concatenate(affineTransform(panel.getInnerDimension(panel.getDrawsize()), panel.getInnerDimension(panel.getSize())));
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
