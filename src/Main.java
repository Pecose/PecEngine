import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import display.Brush;
import display.Mouse;
import display.PecEngine;
import display.Starter;
import display.Window;
import shapes.Ellipse;
import shapes.Radial;
import shapes.Rectangle;
import sound.Sound;

public class Main implements PecEngine{
	
	public Window window;
	public Radial ellipseA;
	public Ellipse ellipseB;
	public Rectangle rec;
	int width = 10;
	int height = 10;

	Sound plouf = new Sound("son.wav");	
	
	public static void main(String[] arg){ Starter.start(new Main()); }

	@Override
	public void Creation(Window window) {
		this.window = window;
		ellipseA = new Radial(width, height, Color.red, Color.gray, 50);
		ellipseB = new Ellipse(width, height, Color.red, 2);
		rec = new Rectangle(width, height, Color.green);
		
	}

	@Override
	public void Display(Brush brush, Mouse mouse) {
		
		brush.drawShape(ellipseA, 30, 35);
		brush.drawShape(ellipseB, 50, 25);
		brush.drawShape(rec, mouse.getPosition());

	}
	
	@Override
	public void Keyboard(KeyEvent pressed, KeyEvent released, KeyEvent typed) {
		if(pressed != null){
			System.out.println(pressed.getKeyCode());
			if(pressed.getKeyCode()==68){ //D
				width++;
			}else if(pressed.getKeyCode()==90){ //Z
				height++;
			}else if(pressed.getKeyCode()==81 && width > 1){ //Q
				width--;
			}else if(pressed.getKeyCode()==83 && height > 1){ //S
				height--;
			}else if(pressed.getKeyCode()==27){ //Space
				window.setFullScreen();
			}else if(pressed.getKeyCode()==38){ //UP
				plouf.upVolume();
			}else if(pressed.getKeyCode()==37){ //LEFT
				plouf.stop();
			}else if(pressed.getKeyCode()==40){ //DOWN
				plouf.downVolume();
			}else if(pressed.getKeyCode()==39){ //RIGHT
				plouf.play();
			}else{
				return;
			}
			ellipseA.reload(width, height);
			ellipseB.reload(width, height);
		}
	}

	@Override
	public void Mouse(MouseEvent pressed, MouseEvent released, MouseEvent clicked, MouseWheelEvent wheel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Screen(MouseEvent entered, MouseEvent exited, MouseEvent dragged) {
		// TODO Auto-generated method stub
		
	}
	
}
