package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import tools.Serializer;
import waifUPNP.UPnP;

public class Client extends Thread{
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private String login;
	private ArrayList<String> logins = new ArrayList<String>();
	private ClientManager clientManager;
	
	public ArrayList<String> getLogins(){ return logins; }
	public String getLogin(){ return login; }
	
	public void close(){
		try {
			if(socket.isConnected()){ socket.close(); }
		}catch(Exception e){ e.printStackTrace(); }
	}
	
	public void sendTo(String login, Serializable message){
		this.send("["+ login +"]".concat(Serializer.serializeString(message)));
	}
	
	public void sendText(String string){
		this.send("//".concat(string));
	}
	
	public void send(Serializable message){
		try { 
			dataOutputStream.writeUTF(Serializer.serializeString(message));
		}catch(Exception e){ e.printStackTrace(); }
	}
	
	public void run(){
		try{ 
			System.out.println("[" + login + "] Vous êtes connecté");
			while(true){
				String colis = dataInputStream.readUTF();
				if(colis.substring(0, 2).equals("//")){
					System.out.println(colis.substring(2));
				}else if(colis.substring(0, 2).equals(">>")){
					KeySet set = (KeySet)Serializer.deserializeString(colis.substring(2));
					this.logins = (ArrayList<String>)set.getLogins();
				}else{
					this.clientManager.Client(Serializer.deserializeString(colis));
				}
			} 
		}catch(Exception e){
			if(e.getMessage() != null){
				if(e.getMessage().equals("Socket closed")){
					System.out.println("[" + login + "] Vous êtes déconnecté");
				}else{ e.printStackTrace(); }
			}else{ e.printStackTrace(); }
		}
	}
	
	public Client(String InetAdress, int port, String login, ClientManager clientManager){
		try{
			UPnP.openPortTCP(port);
			this.clientManager = clientManager;
			this.login = login;
			this.socket = new Socket(InetAdress, port);
			this.dataInputStream = new DataInputStream(socket.getInputStream());
			this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
			this.dataOutputStream.writeUTF(login);
			this.start();
		}catch(Exception e){ e.printStackTrace(); }
	}
	
}
