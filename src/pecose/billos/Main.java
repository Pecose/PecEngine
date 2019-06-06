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
	public Ellipse ellipse;
	int width = 100;
	int height = 100;
	
	public static void main(String[] arg){ Starter.start(new Main()); }

	@Override
	public void Creation(Window window) {
		this.window = window;
		this.mouse = new Mouse(window);
		System.out.println(System.currentTimeMillis());
		ellipse = new Ellipse(width, height, Color.red, new Color(255, 0,0,0), 80);
		System.out.println(System.currentTimeMillis());
	}

	@Override
	public void Display(Brush brush) {
		brush.reset();
		brush.drawShape(ellipse, this.mouse.getPosition());

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
			ellipse = new Ellipse(width, height, Color.red, new Color(255, 0,0,0));
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
