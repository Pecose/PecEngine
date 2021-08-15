package rmiserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import tools.Serializer;

public class ClientConnexion {
	private final Stream stream;
	
	public Stream getStream() { return stream; }
	public Socket getSocket() { return stream.getSocket(); }
	public DataInputStream getDataInputStream() { return stream.getDataInputStream(); }
	public DataOutputStream getDataOutputStream() { return stream.getDataOutputStream(); }

	public ClientConnexion(String address, int port) throws UnknownHostException, IOException{
		this.stream = new Stream(new Socket(address, port));
	}
	
	public Serializable invoke(Serializable message) throws IOException{
		return this.invoke(Serializer.serializeString(message));
	}
	
	public Serializable invoke(String string) throws IOException{
		stream.write(string);
		return stream.read();
	}
	
	public void close() throws IOException{
		stream.close();
	}
}
