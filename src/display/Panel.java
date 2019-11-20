package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Panel extends JPanel{  
	private static final long serialVersionUID = -696807925661491890L;
	public Frame frame;
	private boolean proportional = true;
	private Dimension drawsize;
	
	public Panel(PecEngine pecEngine){
		Panel p = this;
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run(){ p.frame = new Frame(pecEngine, p); }
		});
		
		this.drawsize = new Dimension(frame.getScreenWidth(), frame.getScreenHeight());
		this.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10,this.getBackground()));
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(300, 300));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try{
			Graphics2D g2 = (Graphics2D) g.create();
			Insets insets = getInsets();
			g2.transform(AffineTransform.getTranslateInstance(insets.left, insets.top));
		
			if (proportional) { adapt(g2); }
			frame.pecEngine.display(this, g2);
			g2.dispose();
			repaint();
		}catch(Exception e){} 
	}
	
	//:::::::::::::::::::::::::::::::::::::::::::::::
	
		private Dimension getInnerDimension(Dimension dimension) {
			Insets insets = getInsets();
			return new Dimension(Math.max(0, dimension.width-insets.left-insets.right), 
					Math.max(0, dimension.height-insets.top-insets.bottom));
		}
		
		private void proportionalAffineTransform(Graphics2D g2d, Dimension fromsize, Dimension tosize) {
			double sx = tosize.getWidth()/fromsize.getWidth();
			double height = fromsize.getHeight()*sx;
			if ( height > tosize.getHeight() ) {
				sx = tosize.getHeight()/fromsize.getHeight();
			}
			g2d.transform(AffineTransform.getScaleInstance(sx, sx));
		}
		
		private void adapt(Graphics2D g2d) {
			proportionalAffineTransform(g2d, getInnerDimension(drawsize), getInnerDimension(getSize()));
		}
		
		//::::::::::::::::::::::::::::::::::::::::
}

