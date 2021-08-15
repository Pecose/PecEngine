package rmiserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

import tools.Serializer;

public class Stream {
	
	private final Socket socket;
	private final DataInputStream dataInputStream;
	private final DataOutputStream dataOutputStream;
	
	public Socket getSocket() { return socket; }
	public DataInputStream getDataInputStream() { return dataInputStream; }
	public DataOutputStream getDataOutputStream() { return dataOutputStream; }
	
	public Stream(Socket socket) throws IOException {
		this.socket = socket;
		this.dataInputStream = new DataInputStream(socket.getInputStream());
		this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
	}

	public void write(Serializable message) throws IOException {
		this.write(Serializer.serializeString(message));
	}
	
	public void write(String message) throws IOException {
		if(!socket.isClosed() && !socket.isInputShutdown()){
			this.dataOutputStream.writeUTF(message);
			this.dataOutputStream.flush();
		}
	}
	
	public Serializable read() throws IOException {
		return Serializer.deserializeString(this.readString());
	}
	
	public String readString() throws IOException {
		if(!socket.isClosed() && !socket.isInputShutdown()){
			return dataInputStream.readUTF();
		} return null;
	}
	
	public String getIPAddress(){ 
		return socket.getRemoteSocketAddress().toString(); 
	}
	
	public void close() throws IOException{
		dataInputStream.close();
		dataOutputStream.close();
		socket.close();
	}

}
