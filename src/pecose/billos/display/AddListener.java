package pecose.billos.display;

import java.awt.event.WindowEvent;

public interface AddListener {
	public void Window(WindowEvent closing, WindowEvent opened, WindowEvent closed);
	public void Taskbar(WindowEvent iconified, WindowEvent deiconified);
	public void Desktop(WindowEvent activated, WindowEvent deactivated);
}
