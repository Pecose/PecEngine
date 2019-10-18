package display;

import java.awt.event.ComponentListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowListener;

public interface PecEngine extends KeyListener, MouseListener, MouseWheelListener, MouseMotionListener, WindowListener, ComponentListener{
	
	public static void start(PecEngine pecEngine){ new Panel(pecEngine); }
	
	public void creation(Window window);
	public void display(Brush brush, Mouse mouse);
}