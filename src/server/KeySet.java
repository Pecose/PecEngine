package server;
import java.io.Serializable;
import java.util.ArrayList;

public class KeySet implements Serializable{
	private static final long serialVersionUID = 3088112606730245160L;
	private ArrayList<String> logins = new ArrayList<String>();
	private transient ArrayList<Connection> connections = new ArrayList<Connection>();
	public ArrayList<String> getLogins(){ return logins; }
	public ArrayList<Connection> getConnections(){ return connections; }
	
	public void closeAll(){
		for(String s : logins){ 
			this.remove(s);
		}
	}
	
	public Boolean contains(String login){
		if(logins.contains(login)){
			return true;
		} return false;
	}
	
	public Connection getConnection(String login){ 
		if(contains(login)){
			return connections.get(logins.indexOf(login));
		} return null;
	}
	
	public void add(String login, Connection connection){ 
		if(!contains(login)){
			this.logins.add(login);
			this.connections.add(connection);
		}
	}
	
	public void remove(String login){ 
		if(contains(login)){
			int index = logins.indexOf(login);
			this.logins.remove(index);
			this.connections.remove(index);
		}
	}
}