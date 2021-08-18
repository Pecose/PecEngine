package rmiserver;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Proxy;

public class Client{
	
	private ClientConnexion connexion;
	private int port;
	
	public int getPort() {
		return this.port;
	}
	
	public Client(String address){
		this(address, 1900);
	}
	
	public Client(String address, int port){
		try {
			this.port = port;
			this.connexion = new ClientConnexion(address, port);
		} catch (IOException e) { System.err.println("[Client] " + e.getMessage()); }
	}
	
	public Serializable getRemote(Serializable target, String name) {
		try {
			NewInstance newInstance = new NewInstance(target, name);
			this.connexion.getStream().write(newInstance);
			Class<?>[] c = target.getClass().getInterfaces();
			return (Serializable) Proxy.newProxyInstance(c[0].getClassLoader(), c, new LoggingHandler(name, connexion));
		} catch (IOException e) { System.err.println("[Client getRemote] " + e.getMessage()); }
		return null;
	}
	
	public void close(){ 
		try { 
			this.connexion.close();
			System.out.println("Client closed");
		}catch (Exception e) { System.err.println("[Client Close] " + e.getMessage()); }
	}
	
}
