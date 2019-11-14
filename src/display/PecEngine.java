package display;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public interface PecEngine{
	
	public static void start(PecEngine pecEngine){ new Panel(pecEngine); }
	
	public void creation(JFrame f, Mouse mouse);
	public void display(JPanel p, Graphics2D g);
}