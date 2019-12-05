package display;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.ImageObserver;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
 
public class ScaleAdaptablePanel2 extends JPanel {
 
	private static final long serialVersionUID = 1L;
 
	private boolean proportional;
	private boolean adaptable;
	private final BiConsumer<Graphics2D,ImageObserver> painter;
	private final Dimension drawsize;
	private EventListenerList eventlisteners;
 
	public ScaleAdaptablePanel2(BiConsumer<Graphics2D,ImageObserver> painter, int width, int height) {
		this.eventlisteners = new EventListenerList();
		this.painter=painter;
		this.proportional=true;
		this.adaptable=true;
		this.drawsize = new Dimension(width, height);
		MouseAdapter mouseAdapter = new MouseAdapter() { 
 
			@Override
			public void mouseClicked(MouseEvent e) {
				propagate(e, getMouseListeners(), MouseListener::mouseClicked);
			}
 
			@Override
			public void mousePressed(MouseEvent e) {
				propagate(e, getMouseListeners(), MouseListener::mousePressed);
			}
 
			@Override
			public void mouseReleased(MouseEvent e) {
				propagate(e, getMouseListeners(), MouseListener::mousePressed);
			}
 
			@Override
			public void mouseEntered(MouseEvent e) {
				propagate(e, getMouseListeners(), MouseListener::mouseEntered);
			}
 
			@Override
			public void mouseExited(MouseEvent e) {
				propagate(e, getMouseListeners(), MouseListener::mouseExited);
			}
 
			@Override
			public void mouseMoved(MouseEvent e) {
				propagate(e, getMouseMotionListeners(), MouseMotionListener::mouseMoved);
			}
 
			@Override
			public void mouseDragged(MouseEvent e) {
				propagate(e, getMouseMotionListeners(), MouseMotionListener::mouseDragged);
			}
 
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				propagate(e, getMouseWheelListeners(), MouseWheelListener::mouseWheelMoved);
			}
 
			private void propagate(MouseEvent mouseEvent, MouseListener[] listeners, BiConsumer<MouseListener, MouseEvent> distatcher) {
				Point mousePoint = mouseEvent.getPoint();
				try {
					convertMouse(mousePoint); 
					MouseEvent newEvent = createMouseEvent(mouseEvent, mousePoint);
					synchronized (ScaleAdaptablePanel2.this) {
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
					synchronized (ScaleAdaptablePanel2.this) {
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
					synchronized (ScaleAdaptablePanel2.this) {
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
		};
		super.addMouseListener(mouseAdapter);
		super.addMouseMotionListener(mouseAdapter);
		super.addMouseWheelListener(mouseAdapter);
	}
 
	public void addComponentMouseListener(MouseListener listener) {
		super.addMouseListener(listener);
	}
 
	public void removeComponentMouseListener(MouseListener l) {
		super.removeMouseListener(l);
	}
 
	public void addComponentMotionListener(MouseMotionListener listener) {
		super.addMouseMotionListener(listener);
	}
 
	public void removeComponentMotionListener(MouseMotionListener listener) {
		super.removeMouseMotionListener(listener);
	}
 
	public void addComponentMouseWheelListener(MouseWheelListener listener) {
		super.addMouseWheelListener(listener);
	}
	public void removeComponentMouseWheelListener(MouseWheelListener listener) {
		super.removeMouseWheelListener(listener);
	}
 
	@Override
	public synchronized MouseListener[] getMouseListeners() {
		return eventlisteners.getListeners(MouseListener.class);
	}
 
	@Override
	public synchronized void addMouseListener(MouseListener l) {
		if (l == null) {
            return;
        }
		eventlisteners.add(MouseListener.class, l);
	}
 
	@Override
	public synchronized void removeMouseListener(MouseListener l) {
		if (l == null) {
            return;
        }
		eventlisteners.remove(MouseListener.class, l);
	}
 
	@Override
	public synchronized MouseMotionListener[] getMouseMotionListeners() {
		return eventlisteners.getListeners(MouseMotionListener.class);
	}
 
	@Override
	public synchronized void addMouseMotionListener(MouseMotionListener l) {
		if (l == null) {
            return;
        }
		eventlisteners.add(MouseMotionListener.class, l);
	}
 
	@Override
	public synchronized void removeMouseMotionListener(MouseMotionListener l) {
		if (l == null) {
            return;
        }
		eventlisteners.remove(MouseMotionListener.class, l);
	}
 
	@Override
	public synchronized MouseWheelListener[] getMouseWheelListeners() {
		return eventlisteners.getListeners(MouseWheelListener.class);
	} 
 
	@Override
	public synchronized void addMouseWheelListener(MouseWheelListener l) {
		if (l == null) {
            return;
        }
		eventlisteners.add(MouseWheelListener.class, l);
	}
 
	@Override
	public synchronized void removeMouseWheelListener(MouseWheelListener l) {
		if (l == null) {
            return;
        }
		eventlisteners.remove(MouseWheelListener.class, l);
	}
 
	public void setProportional(boolean proportional) {
		boolean old = this.proportional;
		this.proportional=proportional;
		if ( old!=proportional ) {
			firePropertyChange("PROPORTIONAL", old, proportional);
			repaint();
		}
	}
 
	public boolean isProportional() {
		return proportional;
	}
 
	public Point getAdapterMousePoint() {
		Point point = getMousePosition();
		try {
			convertMouse(point);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		return point;
	}
 
	public void setAdaptable(boolean adaptable) {
		boolean old = this.adaptable;
		this.adaptable=adaptable;
		if ( old!=adaptable ) {
			firePropertyChange("ADAPTABLE", old, adaptable);
			repaint();
		}
	}
 
	public boolean isAdaptable() {
		return adaptable;
	}
 
	private Dimension getInnerDimension(Dimension dimension) {
		Insets insets=getInsets();
		return new Dimension(Math.max(0, dimension.width-insets.left-insets.right), 
				Math.max(0, dimension.height-insets.top-insets.bottom));
	}
 
	@Override
	public void paint(Graphics g) {
		super.paint(g);
 
		if ( painter!=null) {
 
			Graphics2D g2D = (Graphics2D) g.create();		
 
			Insets insets=getInsets();
			g2D.transform(AffineTransform.getTranslateInstance(insets.left, insets.top));
 
			if ( adaptable ) adapt(g2D);
			painter.accept(g2D,this);
 
			g2D.dispose();
 
		}
 
	}
 
	private void adapt(Graphics2D g2d) {
		proportionalAffineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getPreferredSize()));
		if ( proportional ) {
			adaptProportional(g2d);
		} else {
			affineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getSize()));
		}
	}
 
	private void adaptProportional(Graphics2D g2d) {
		proportionalAffineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getSize()));
	}
 
	private void affineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) { 
		g2d.transform(affineTransform(fromsize, tosize));
	}
 
	private AffineTransform affineTransform(Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth();
		double sy = tosize.getHeight()/fromsize.getHeight();
		return AffineTransform.getScaleInstance(sx, sy);
	}
 
	private void proportionalAffineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
		g2d.transform(proportionalAffineTransform(fromsize,tosize));
	}
 
	private AffineTransform proportionalAffineTransform(Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth();
		double height = fromsize.getHeight()*sx;
		if ( height > tosize.getHeight() ) {
			sx = tosize.getHeight()/fromsize.getHeight(); 
		}
		return AffineTransform.getScaleInstance(sx, sx);
	}
 
	protected void convertMouse(Point point) throws NoninvertibleTransformException {
		Insets insets=getInsets();
		AffineTransform transform = AffineTransform.getTranslateInstance(insets.left, insets.top);
		if ( adaptable ) {
			transform.concatenate(proportionalAffineTransform(getInnerDimension(drawsize), getInnerDimension(getPreferredSize())));
			if ( proportional ) {
				transform.concatenate(proportionalAffineTransform(getInnerDimension(drawsize), getInnerDimension(getSize())));
			}
			else {
				transform.concatenate(affineTransform(getInnerDimension(drawsize), getInnerDimension(getSize())));
			}
		}  
		transform.inverseTransform(point, point); 
	}
 
	// démo
 
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		JPanel main = new JPanel(new BorderLayout());
 
		Dimension dimension = new Dimension(260,180);
		ScaleAdaptablePanel2 panel = new ScaleAdaptablePanel2(ScaleAdaptablePanel2::paint,dimension.width,dimension.height);
		panel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,panel.getBackground()));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(dimension);
 
		panel.setEnabled(true);
		MouseAdapter mouseAdapter = new MouseAdapter() {
 
			Rectangle rectangle = new Rectangle(120, 80, 100, 40);
			Ellipse2D oval = new Ellipse2D.Double(130, 20, 50, 50);
			Rectangle imageBounds = image==null?null:new Rectangle(10,50, image.getWidth(panel), image.getHeight(panel));
			
			//met une main ou une fleche en fonction de ce qui est survolé par le curseur
			@Override 
			public void mouseMoved(MouseEvent e) {
				rectangleHover = rectangle.contains(e.getPoint());
				ovalHover = oval.contains(e.getPoint());
				int cursor;
				if ( rectangleHover ) {
					cursor = Cursor.HAND_CURSOR;
				}
				else if ( ovalHover ) {
					cursor = Cursor.HAND_CURSOR;
				}
				else if ( imageBounds!=null && imageBounds.contains(e.getPoint()) ) {
					cursor = Cursor.HAND_CURSOR;
				}
				else {
					cursor = Cursor.getDefaultCursor().getType();
				}
				panel.setCursor(Cursor.getPredefinedCursor(cursor));
				panel.repaint();
			}
 
			//Entoure la partie cliquée en violet
			@Override
			public void mouseClicked(MouseEvent e) {
				if ( e.getClickCount()==1 && e.getButton()==MouseEvent.BUTTON1 ) {
					toggleSelection(rectangle, e);
					toggleSelection(oval, e);
					toggleSelection(imageBounds, e);
					panel.repaint();
				}
			}
 
			private void toggleSelection(Shape shape, MouseEvent e) {
				if ( shape!=null && shape.contains(e.getPoint()) ) {
					if( selection.contains(shape) ) {
						selection.remove(shape);
					}else{
						selection.add(shape);
					}
				} 
			}
 
 
		};
		panel.addMouseListener(mouseAdapter);
		panel.addMouseMotionListener(mouseAdapter);
 
		main.add(panel);
 
		JPanel ui = new JPanel(new GridBagLayout());
 
		JCheckBox checkboxProportional = new JCheckBox("Adaptation proportionelle");
		checkboxProportional.setSelected(panel.isProportional());
		checkboxProportional.addActionListener(e->panel.setProportional(checkboxProportional.isSelected()));
		ui.add(checkboxProportional);
 
		JCheckBox checkboxAdaptable = new JCheckBox("Adaptation");
		checkboxAdaptable.setSelected(panel.isAdaptable());
		checkboxAdaptable.addActionListener(e->panel.setAdaptable(checkboxAdaptable.isSelected()));
		ui.add(checkboxAdaptable);
 
		main.add(ui, BorderLayout.SOUTH);
 
		frame.add(main);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
 
	}
 
	private static Image image = loadImage();
	private static Image loadImage() {
		try {
			return ImageIO.read(ScaleAdaptablePanel.class.getResource("suricate.jpg"));
		} catch (Throwable e) {
			return null;
		}
	}
	private static Font font = new Font("SansSerif",Font.PLAIN,40);
	private static boolean rectangleHover;
	private static boolean ovalHover;
	private static Set<Shape> selection = new HashSet<>();
	private static void paint(Graphics2D g, ImageObserver observable) {
		/*g.setColor(Color.LIGHT_GRAY);
		g.drawRect(0,0,240,160);*/
		g.setColor(Color.BLUE);
		g.setFont(font);
		g.drawString("Hello", 20,30);
		g.setColor(Color.RED);
		g.drawRect(120, 80, 100, 40);
		if ( !ovalHover ) {
			g.setColor(Color.ORANGE);
			g.fillOval(130, 20, 50, 50);
		}
 
		if( image!=null) {
			g.drawImage(image,10,50,observable);
		}
		if ( !selection.isEmpty() ) {
			g.setColor(Color.MAGENTA);
			AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.5f);
			Graphics2D g2d = (Graphics2D) g.create();
			try {
				g2d.setStroke(new BasicStroke(3));
				for(Shape selected : selection) {
					g2d.draw(selected);
				}
				g2d.setComposite(alpha);
				for(Shape selected : selection) {
					g2d.fill(selected);
				}
			}
			finally {
				g2d.dispose();
			}
		}
		if ( rectangleHover ) {
			g.setColor(Color.YELLOW);
			g.fillRect(120+1, 80+1, 100-2, 40-2);
		}
 
		if ( ovalHover ) {
			g.setColor(Color.GREEN);
			g.fillOval(130, 20, 50, 50);
		}
	}
 
	// fin démo
 
}