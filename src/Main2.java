import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

import display.Brush;
import display.Mouse;
import display.PecEngine;
import display.Window;
import server.Server;

public class Main2 implements PecEngine{
	
	public Server server;
	public static void main(String[] args){
		PecEngine.start(new Main2());
	}

	@Override
	public void creation(Window window){
		server = new Server(1813, new MonServer());
	}

	@Override
	public void display(Brush brush, Mouse mouse){
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0){
		// TODO Auto-generated method stub
		
	}

	

}
