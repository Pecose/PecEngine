package server;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;

import waifUPNP.UPnP;

public class Server extends Thread{
	
	private ServerSocket serverSocket;
	private KeySet keySet = new KeySet();
	private ServerManager serverManager;
	
	protected ServerManager getServerManager(){ return serverManager; }
	public KeySet getKeySet(){ return keySet; }
	public static String localhost() { 
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}catch(Exception e){e.printStackTrace(); } 
		return null;
	}
	
	public void sendTo(String login, Serializable message){
		keySet.getConnection(login).send(message);
	}
	
	public void sendAll(Serializable message){
		for(Connection c : keySet.getConnections()){
			c.send(message);
		}
	}
	
	protected void sendAllKeySet(){
		for(Connection c : keySet.getConnections()){
			c.sendKeySet();
		}
	}

	protected Connection getConnection(String login){
		return keySet.getConnection(login);
	}
	
	public void closeServer(){ 
		try { 
			serverSocket.close();
			keySet.closeAll();
			keySet = null;
			System.out.println("Le serveur est fermer.");
		}catch (Exception e) { System.err.println("[Server CloseServer] " + e.getMessage()); }
	}
	
	public void run(){
		try { 
			while(!serverSocket.isClosed()){
				new Connection(serverSocket.accept(), this);
				sendAllKeySet();
			}
		}catch (Exception e) { System.err.println("[Server Run] " + e.getMessage()); }
	}
	
	public Server(int port, ServerManager serverManager){ 
		try { 
			UPnP.openPortTCP(port);
			this.serverManager = serverManager;
			serverSocket = new ServerSocket(port);
		}catch (Exception e) { System.err.println("[Server] " + e.getMessage()); }
		System.out.println("Le serveur est démarré.");
		this.start();
	}
}
