import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map.Entry;

import display.Brush;
import display.Mouse;
import display.PecEngine;
import display.Window;
import server.Client;
import server.Server;

public class Main implements PecEngine{
	
	public Server server;
	public Client client;
	public HomeServer homeServer = new HomeServer();
	public HomeClient homeClient = new HomeClient();
	public Player player;
	public HashMap<String, Player> playersMap;
	
	public static void main(String[] args){
		PecEngine.start(new Main());
	}

	@Override
	public void creation(Window window){
		player = new Player("Pecose", Color.red, new Point2D.Double(0, 0));
		homeServer.setMain(this);
	}

	@Override
	public void display(Brush brush, Mouse mouse){
		player.setPosition(mouse.getPosition());
		if(client != null) {
			client.send(player);
		}
		
		for(Entry<String, Player> player : playersMap.entrySet()) {
			Player p = player.getValue();
			brush.graphics.setColor(p.getTeam());
			brush.graphics.fill(new Ellipse2D.Double(p.getPosition().getX(), p.getPosition().getY(), 10, 10));
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent key){
		if(key.getKeyChar() == 's') {
			server = new Server(1813, homeServer);
			System.out.println("Vous venez de créé un serveur");
		}else if(key.getKeyChar() == 'c'){
			client = new Client("10.234.100.23", 1813, "Pecose", homeClient);
			System.out.println("Vous venez de créé un client");
		}
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
		System.exit(0);
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
