package pecose.billos;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import pecose.billos.display.Brush;
import pecose.billos.display.Mouse;
import pecose.billos.display.PecEngine;
import pecose.billos.display.Position;
import pecose.billos.display.Starter;
import pecose.billos.display.Window;
import pecose.billos.display.shape.Ellipse;

public class Main implements PecEngine{
	
	public Window window;
	public Mouse mouse;
	public Ellipse ellipseA;
	public Ellipse ellipseB;
	int width = 10;
	int height = 10;
	
	public static void main(String[] arg){ Starter.start(new Main()); }

	@Override
	public void Creation(Window window) {
//		window.setSize(700, 700);
		this.window = window;
		this.mouse = new Mouse(window);
		
		ellipseA = new Ellipse(width, height, Color.red, new Color(255, 0,0,0), 2);
		ellipseB = new Ellipse(width, height, Color.red, new Color(255, 0,0,0), 100);
	}

	@Override
	public void Display(Brush brush) {
		
		brush.drawShape(ellipseA, 30, 35);
		brush.drawShape(ellipseB, 50, 35);

	}
	
	@Override
	public void Keyboard(KeyEvent pressed, KeyEvent released, KeyEvent typed) {
		if(pressed != null){
			System.out.println(pressed.getKeyCode());
			if(pressed.getKeyCode()==68){
				width++;
			}else if(pressed.getKeyCode()==90){
				height++;
			}else if(pressed.getKeyCode()==81 && width > 1){
				width--;
			}else if(pressed.getKeyCode()==83 && height > 1){
				height--;
			}else{
				return;
			}
			ellipseA.reload(width, height);
		}
	}

	@Override
	public void Mouse(MouseEvent pressed, MouseEvent released, MouseEvent clicked, MouseWheelEvent wheel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Screen(MouseEvent move, MouseEvent entered, MouseEvent exited, MouseEvent dragged) {
	}
	
}
