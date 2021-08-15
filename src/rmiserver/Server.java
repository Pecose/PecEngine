package rmiserver;

import java.io.Serializable;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
	private int port;
	private transient HashMap<String, ServerConnexion> connexions = new HashMap<String, ServerConnexion>();
	private transient HashMap<String, Serializable> remoteObjects = new HashMap<String, Serializable>();
	
	public HashMap<String, ServerConnexion> getConnections(){ return this.connexions; }
	public Serializable getRemoteObject(String s){ return this.remoteObjects.get(s); }
	public Serializable addRemoteObject(String name, Serializable target) {
		return this.remoteObjects.put(name, target);
	}
	
	public int getPort() { return this.port; }
	
	public Server(){ this(1900); }
	public Server(int port){ 
		this.port = port;
		try { 
			serverSocket = new ServerSocket(port);
			System.out.println("Server launched");
			this.start();
		}catch (Exception e) { System.err.println("[Server] " + e.getMessage()); }
	}
	
	public void run(){
		try { 
			while(!serverSocket.isClosed()){
				ServerConnexion c = new ServerConnexion(serverSocket.accept(), this);
				this.connexions.put(c.getStream().getIPAddress(), c);
			}
		}catch (Exception e) { System.err.println("[Server Run] " + e.getMessage()); }
	}
	
	public void close(){ 
		try { 
			serverSocket.close();
			for(Map.Entry<String, ServerConnexion> e : connexions.entrySet()){
				e.getValue().close();
			}
			System.out.println("Server closed");
		}catch (Exception e) { System.err.println("[Server Close] " + e.getMessage()); }
	}
	
}
