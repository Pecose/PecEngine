package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.Socket;

import tools.Serializer;

public class Connection extends Thread{
	
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private Server server;
	private String login;
	private int key;
	private Socket socket;
	
	protected String getLogin(){ return login; }
	protected int getKey(){ return key; }
	
	protected DataOutputStream getDataOutputStream(){
		return dataOutputStream;
	}
	
	protected void close(){
		try {
			if(server.getKeySet().contains(login)){
				System.out.println("Login " + login + " supprimé.");
				server.getKeySet().remove(login);
				
			}else{
				System.out.println("Login " + login + " déjà supprimé.");
			}
			
			server.sendAllKeySet();
			dataInputStream.close();
			dataOutputStream.close();
		}catch(Exception e){ e.printStackTrace(); }
	}
	
	public void sendText(String string){
		this.send("//".concat(string));
	}
	
	protected void sendKeySet(){
		this.send(">>".concat(Serializer.serializeString(server.getKeySet())));
	}
	
	public void send(Serializable message){
		this.send(Serializer.serializeString(message));
	}
	
	public void send(String string){
		try { 
			if(!socket.isClosed() && !socket.isInputShutdown()){
				this.dataOutputStream.writeUTF(string);
			}
		}catch(Exception e){ e.printStackTrace(); }
	}
	
	public void run(){
		try{ 
			while(true){
				String colis = (String) Serializer.deserializeString(dataInputStream.readUTF());
				if(colis.substring(0, 2).equals("//")){
					System.out.println(colis.substring(2));
				}else if(colis.substring(0, 2).equals(">>")){
					server.sendAllKeySet();
				}else if(colis.substring(0, 1).equals("[")){
					boolean b = true; int i = 0;
					colis = colis.substring(1);
					while(b){
						if(colis.substring(i, i+1).equals("]")){
							 b = false;
						}else{ i++; }
					}
					String recipient = colis.substring(0, i);
					colis = colis.substring(i+1);
					server.getConnection(recipient).send(colis);
				}else{
					this.server.getServerManager().Server(colis);
				}
			} 
		}catch (Exception e) { 
			if(e.getMessage() == null){
				this.close();
				System.out.println("["+login+"] s'est déconnecté");
			}else if(e.getMessage().equals("Socket closed") || e.getMessage().equals("Connection reset")){
				this.close();
				System.out.println("["+login+"] s'est déconnecté");
			}else{
				e.printStackTrace();
			}
		} 
	}
	
	protected Connection(Socket socket, Server server){
		try { 
			this.socket = socket;
			this.server = server;
			this.dataInputStream = new DataInputStream(socket.getInputStream());
			this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
			this.login = this.dataInputStream.readUTF();
			this.server.getKeySet().add(login, this);
			this.start();
		}catch (Exception e) { System.err.println("[Connection] " + e.getMessage()); }
	}
}
