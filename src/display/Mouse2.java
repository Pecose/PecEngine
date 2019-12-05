package display;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.function.BiConsumer;

public class Mouse2 extends MouseAdapter{
	
	private Frame frame;
	public Mouse2(Frame frame){ this.frame = frame; }
	
	public int getX(){ return MouseInfo.getPointerInfo().getLocation().x - frame.getComponent(0).getX() - frame.getX(); };
	public int getY(){ return MouseInfo.getPointerInfo().getLocation().y - frame.getComponent(0).getY() - frame.getY(); };
	
	public Point2D getPosition(){ return new Position(getX(), getY()).getPoint2D(); }
	 
	@Override
	public void mouseClicked(MouseEvent e) {
		propagate(e, frame.getMouseListeners(), MouseListener::mouseClicked);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		propagate(e, frame.getMouseListeners(), MouseListener::mousePressed);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		propagate(e, frame.getMouseListeners(), MouseListener::mousePressed);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		propagate(e, frame.getMouseListeners(), MouseListener::mouseEntered);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		propagate(e, frame.getMouseListeners(), MouseListener::mouseExited);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		propagate(e, frame.getMouseMotionListeners(), MouseMotionListener::mouseMoved);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		propagate(e, frame.getMouseMotionListeners(), MouseMotionListener::mouseDragged);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		propagate(e, frame.getMouseWheelListeners(), MouseWheelListener::mouseWheelMoved);
	}

	private void propagate(MouseEvent mouseEvent, MouseListener[] listeners, BiConsumer<MouseListener, MouseEvent> distatcher) {
		Point mousePoint = mouseEvent.getPoint();
		try {
			convertMouse(mousePoint); 
			MouseEvent newEvent = createMouseEvent(mouseEvent, mousePoint);
			synchronized (Mouse2.this) {
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
			synchronized (Mouse2.this) {
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
			synchronized (Mouse2.this) {
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
		Insets insets=frame.getInsets();
		Panel p = frame.panel;
		AffineTransform transform = AffineTransform.getTranslateInstance(insets.left, insets.top);
		if ( frame.panel.isAdaptable() ) {
			transform.concatenate(proportionalAffineTransform(p.getInnerDimension(p.getDrawsize()), p.getInnerDimension(p.getPreferredSize())));
			if ( frame.panel.isProportional() ) {
				transform.concatenate(proportionalAffineTransform(p.getInnerDimension(p.getDrawsize()), p.getInnerDimension(p.getSize())));
			}
			else {
				transform.concatenate(affineTransform(p.getInnerDimension(p.getDrawsize()), p.getInnerDimension(p.getSize())));
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
