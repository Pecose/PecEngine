package rmiserver;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class ServerConnexion extends Thread{
	
	private final Server server;
	private final Stream stream;
	
	protected Socket getSocket() { return stream.getSocket(); }
	protected Stream getStream() { return stream; }
	protected DataInputStream getDataInputStream() { return stream.getDataInputStream(); }
	protected DataOutputStream getDataOutputStream() { return stream.getDataOutputStream(); }
	
	public String getNameForRemoteObject(Serializable target) { 
		return this.stream.getIPAddress()+" "+target.getClass().getName(); 
	}

	protected ServerConnexion(Socket socket, Server server) throws IOException{
		this.server = server;
		this.stream = new Stream(socket);
		this.start();
	}
	
	public void run(){
		try{ 
			while(true){
				Serializable serial = stream.read();
				if(serial instanceof NewInstance) {
					NewInstance pack = (NewInstance) serial;
					String name = this.getNameForRemoteObject(pack.getTarget());
					this.server.addRemoteObject(name, pack.getTarget());
					this.stream.write(name);
				}else 
				if(serial instanceof MethodCall) {
					MethodCall pack = (MethodCall) serial;
					this.stream.write(pack.invokeMethod(server.getRemoteObject(pack.getName())));
				}
			} 
		}catch(EOFException e) { }
		catch(IOException e) { e.printStackTrace(); }
	}
	
	public void close() throws IOException{ stream.close(); }
	
}
