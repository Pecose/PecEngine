package A;
import java.io.Serializable;
import java.util.HashMap;

import server.ClientManager;

public class HomeClient implements ClientManager{

	private transient HashMap<String, Player> players = new HashMap<String, Player>();
	public HashMap<String, Player> getPlayers(){ return players; }
	public void setPlayers(HashMap<String, Player> players){ this.players = players; }
	
	@Override
	public void Client(Serializable message){
		
	}

}
