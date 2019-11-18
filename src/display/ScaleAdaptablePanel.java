package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.util.function.BiConsumer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScaleAdaptablePanel extends JPanel {
	
	private boolean proportional;
	private boolean adaptable;
	private final BiConsumer<Graphics2D,ImageObserver> painter;
	private final Dimension drawsize;

	public ScaleAdaptablePanel(BiConsumer<Graphics2D,ImageObserver> painter, int width, int height) {
		this.painter=painter;
		this.proportional=true;
		this.adaptable=true;
		this.drawsize = new Dimension(width, height);
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
			
			Insets insets = getInsets();
			g2D.transform(AffineTransform.getTranslateInstance(insets.left, insets.top));
		
			if (adaptable) { adapt(g2D); }
			
			painter.accept(g2D,this);
			
			g2D.dispose();
		
		}
		
	}

	private void adapt(Graphics2D g2d) {
		proportionalAffineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getPreferredSize()));
		if ( proportional ) {
			adaptProportional(g2d);
		}
		else {
			affineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getSize()));
		}
	}

	private void adaptProportional(Graphics2D g2d) {
		proportionalAffineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getSize()));
	}

	private void affineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth();
		double sy = tosize.getHeight()/fromsize.getHeight();
		g2d.transform(AffineTransform.getScaleInstance(sx, sy));
	}

	private void proportionalAffineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
		double sx = tosize.getWidth()/fromsize.getWidth();
		double height = fromsize.getHeight()*sx;
		if ( height > tosize.getHeight() ) {
			sx = tosize.getHeight()/fromsize.getHeight();
//			double width = tosize.getWidth()*sx;
//			if ( width> tosize.getWidth() ) {
//				System.out.println(sx);
//				//sx = tosize.getWidth()/fromsize.getWidth();
//			}
		}
		g2d.transform(AffineTransform.getScaleInstance(sx, sx));
	}

	// démo
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel main = new JPanel(new BorderLayout());

		Dimension dimension = new Dimension(260,180);
		ScaleAdaptablePanel panel = new ScaleAdaptablePanel(ScaleAdaptablePanel::paint,dimension.width,dimension.height);
		panel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,panel.getBackground()));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(dimension);
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
	private static void paint(Graphics2D g, ImageObserver observable){
		/*g.setColor(Color.LIGHT_GRAY);
		g.drawRect(0,0,240,160);*/
		g.setColor(Color.BLUE);
		g.setFont(font);
		g.drawString("Hello", 20,30);
		g.setColor(Color.RED);
		g.drawRect(120, 80, 100, 40);
		g.setColor(Color.ORANGE);
		g.fillOval(130, 20, 50, 50);
		if( image!=null) {
			g.drawImage(image,10,50,observable);
		}
	}

	// fin démo
	
}