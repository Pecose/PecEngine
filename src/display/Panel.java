package display;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

class Panel extends JPanel{  
	private static final long serialVersionUID = -696807925661491890L;
	public Frame frame;
	public Panel(PecEngine pecEngine){
		this.frame = new Frame(pecEngine, this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try{
			frame.pecEngine.display(this, (Graphics2D) g.create());
			repaint();
		}catch(Exception e){} 
	}
}

