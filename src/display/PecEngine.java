package display;

import java.awt.Graphics2D;

public interface PecEngine{
	
	public static void start(PecEngine pecEngine){ new Panel(pecEngine); }
	
	public void creation(Frame f);
	public void display(Panel p, Graphics2D g);
}