package pecose.billos.display;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface PecEngine{
	public void Creation(Window window);
	public void Display(Brush brush);
	public void Keyboard(KeyEvent pressed, KeyEvent released, KeyEvent typed);
	public void Mouse(MouseEvent pressed, MouseEvent released, MouseEvent clicked, MouseWheelEvent wheel);
	public void Screen(MouseEvent move, MouseEvent entered, MouseEvent exited, MouseEvent dragged);
}