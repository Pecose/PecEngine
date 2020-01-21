package A;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import display.Frame;
import display.Panel;
import display.PecEngine;
import listeners.KeyPressedListener;
import listeners.MouseMovedListener;
import server.Client;
import server.Server;
import tools.Bbb;
import tools.Injector;
import tools.Saver;

public class Main implements PecEngine, KeyPressedListener, MouseMovedListener{

	public Server server;
	public Client client;
	public HomeServer homeServer = new HomeServer();
	public HomeClient homeClient = new HomeClient();
	public Player player;
	public Point2D mouse = new Point2D.Double(0,0);
	
	public static void main(String[] args){ PecEngine.start(new Main()); }

	@Override
	public void creation(Frame f) {
		Injector injector = new Injector("aze.txt");
		Bbb b = (Bbb)injector.construct("Aaa", new Object[] {6});
		System.out.println(b.getI());
		
//		f.setSize(300, 300);
		f.setDisplayOption(Panel.ADAPTABLE);
		f.addMouseMovedListener(this);
		f.addKeyPressedListener(this);
		player = new Player("Pecose", Color.red, new Point2D.Double(0, 0));
		homeServer.setMain(this);
	}
	
	@Override
	public void display(Panel p, Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect((int)this.mouse.getX(), (int)this.mouse.getY(), 60, 60);
		player.setPosition(mouse);
		if(client != null && client.isAlive()) {
			client.send(player);
		}
		
		for(Entry<String, Player> current : homeClient.getPlayers().entrySet()) {
			Player pl = current.getValue();
			g.setColor(pl.getTeam());
			g.fill(new Ellipse2D.Double(pl.getPosition().getX(), pl.getPosition().getY(), 10, 10));
		}
	}

	@Override
	public void keyPressed(KeyEvent key){
		if(key.getKeyChar() == 's' && server == null) {
			server = new Server(1813, homeServer);
			System.out.println("Vous venez de créé un serveur");
		}else if(key.getKeyChar() == 'c' && client == null){
			client = new Client(Server.localhost(), 1813, "Pecose", homeClient);
//			client = new Client("109.24.249.39", 1813, "Pecose", homeClient);
			System.out.println("Vous venez de créé un client");
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouse.setLocation(e.getX(), e.getY());
	}

}
