package display;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

class Panel extends JPanel{  
	private static final long serialVersionUID = -696807925661491890L;
	public Window window;
	public Panel(PecEngine pecEngine){
		this.window = new Window(pecEngine, this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try{
			window.pecEngine.display(new Brush((Graphics2D)g, window), new Mouse(window));
			repaint();
		}catch(Exception e){} 
	}
}

