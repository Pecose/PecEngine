import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;

import display.Brush;
import display.Mouse;
import display.PecEngine;
import display.Window;
import image.Rectangle;
import sound.Sound;

public class Main implements PecEngine{
	
	public Window window;
//	public Radial ellipseA;
//	public Ellipse ellipseB;
	public Rectangle rec;
	public Rectangle im;
	int width = 10;
	int height = 10;

	Sound plouf = new Sound("son.wav");	
	
	public static void main(String[] arg){ PecEngine.start(new Main()); }

	@Override
	public void creation(Window window) {
		this.window = window;
//		ellipseA = new Radial(width, height, Color.red, Color.gray, 50);
//		ellipseB = new Ellipse(width, height, Color.red, 2);
		im = new Rectangle(1500, 1500, Color.BLUE);
		rec = new Rectangle(width, height, Color.green);
		
	}

	@Override
	public void display(Brush brush, Mouse mouse) {
		
//		brush.drawShape(ellipseA, 30, 35);
//		brush.drawShape(ellipseB, 50, 25);
		
		im.add(rec, mouse.getPosition());
		brush.drawImage(im, 0, 0);

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println(e.getKeyCode());
		if(e.getKeyCode()==68){ //D
			width++;
		}else if(e.getKeyCode()==90){ //Z
			height++;
		}else if(e.getKeyCode()==81 && width > 1){ //Q
			width--;
		}else if(e.getKeyCode()==83 && height > 1){ //S
			height--;
		}else if(e.getKeyCode()==27){ //Space
			window.setFullScreen();
		}else if(e.getKeyCode()==38){ //UP
			plouf.upVolume();
		}else if(e.getKeyCode()==37){ //LEFT
			plouf.stop();
		}else if(e.getKeyCode()==40){ //DOWN
			plouf.downVolume();
		}else if(e.getKeyCode()==39){ //RIGHT
			plouf.play();
		}else{
			return;
		}
//		ellipseA.reload(width, height);
//		ellipseB.reload(width, height);
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	
}
