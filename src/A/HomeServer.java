package A;
import java.io.Serializable;
import server.ServerManager;

public class HomeServer implements ServerManager{
	
	private Main main;
	
	@Override
	public void Server(Serializable message){
//		Player player = (Player) message;
//		System.out.println(player);System.exit(0);
		main.server.sendAll(message);
		
	}

	public Main getMain(){
		return main;
	}

	public void setMain(Main main){
		this.main = main;
	}

}
