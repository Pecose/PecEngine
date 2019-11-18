package display;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Panel extends JPanel{  
	private static final long serialVersionUID = -696807925661491890L;
	public Frame frame;
	public Panel(PecEngine pecEngine){
		Panel p = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run(){
				p.frame = new Frame(pecEngine, p);
			}
		});
		
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

