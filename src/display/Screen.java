package display;

import java.awt.Toolkit;

public class Screen{
	public static int getWidth(){ return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(); }
	public static int getHeight(){ return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight(); }
}
