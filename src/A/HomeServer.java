import java.io.Serializable;

import server.ServerManager;

public class HomeServer implements ServerManager{
	
	private Main main;
	
	@Override
	public void Server(Serializable message){
		if(main.playersMap.containsKey(((Player) message).getLogin())) {
			main.playersMap.replace(((Player) message).getLogin(), (Player)message);
		}else {
			main.playersMap.put(((Player) message).getLogin(), (Player)message);
		}
		
	}

	public Main getMain(){
		return main;
	}

	public void setMain(Main main){
		this.main = main;
	}

}
