package A;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import display.Frame;
import display.Panel;
import display.PecEngine;

public class Main implements PecEngine, MouseMotionListener{

	public static void main(String[] args){ PecEngine.start(new Main()); }
	private Frame frame;
	int x=0, y=0;
	
	@Override
	public void creation(Frame f){
		this.frame = f;
		
		
		f.addMouseMotionListener(this);
		
	}

	@Override
	public void display(Panel p, Graphics2D g){
		g.fillRect(200, 200, 300, 300);
		g.fillRect(x, y, 250, 250);
	}

	@Override
	public void mouseDragged(MouseEvent e){
		// TODO Auto-generated method stub
		
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
//			System.out.println("Vous venez de créé un serveur");
//		}else if(key.getKeyChar() == 'c'){
//			client = new Client("10.234.100.23", 1813, "Pecose", homeClient);
//			System.out.println("Vous venez de créé un client");
//		}
//	}
//
//	

}
