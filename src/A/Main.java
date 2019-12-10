package A;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import display.Frame;
import display.Panel;
import display.PecEngine;
import display.Screen;
import listeners.MouseMovedListener;

public class Main implements PecEngine, MouseMovedListener{

	public static void main(String[] args){ PecEngine.start(new Main()); }
	private Frame frame;
	int x=0, y=0;
	
	@Override
	public void creation(Frame f){
		this.frame = f;
		f.setSize(500, 500);
//		f.panel.setSize(new Dimension(500, 500));
		
		f.pack();
		f.setLocationRelativeTo(null);
		f.setFullScreen();
		f.addMouseMovedListener(this);
		System.out.println("Screen Width: "+Screen.getWidth()+" Screen Height: "+Screen.getHeight());
	}

	@Override
	public void display(Panel p, Graphics2D g){
		g.fillRect(0, 0, 1000, 500);
		g.fillRect(x, y, 250, 250);
	}


	@Override
	public void mouseMoved(MouseEvent e){
		x=e.getX();
		y=e.getY();
	}
	
	
//	public Server server;
//	public Client client;
//	public HomeServer homeServer = new HomeServer();
//	public HomeClient homeClient = new HomeClient();
//	public Player player;
//	public HashMap<String, Player> playersMap;
//	
//	public static void main(String[] args){
//		PecEngine.start(new Main());
//	}
//
//	@Override
//	public void creation(Window window){
//		player = new Player("Pecose", Color.red, new Point2D.Double(0, 0));
//		homeServer.setMain(this);
//	}
//
//	@Override
//	public void display(Brush brush, Mouse mouse){
//		player.setPosition(mouse.getPosition());
//		if(client != null) {
//			client.send(player);
//		}
//		
//		for(Entry<String, Player> player : playersMap.entrySet()) {
//			Player p = player.getValue();
//			brush.graphics.setColor(p.getTeam());
//			brush.graphics.fill(new Ellipse2D.Double(p.getPosition().getX(), p.getPosition().getY(), 10, 10));
//		}
//		
//	}
//	
//	@Override
//	public void keyPressed(KeyEvent key){
//		if(key.getKeyChar() == 's') {
//			server = new Server(1813, homeServer);
//			System.out.println("Vous venez de cr�� un serveur");
//		}else if(key.getKeyChar() == 'c'){
//			client = new Client("10.234.100.23", 1813, "Pecose", homeClient);
//			System.out.println("Vous venez de cr�� un client");
//		}
//	}
//
//	

}
