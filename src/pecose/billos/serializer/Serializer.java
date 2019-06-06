package pecose.billos.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public class Serializer {
	
	public static byte[] serialize(Serializable exchange){
		try{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        ObjectOutputStream output = new ObjectOutputStream( outputStream );
	        output.writeObject( exchange );
	        output.close();
	        return outputStream.toByteArray();
		}catch (Exception e) { System.err.println("[Serializer.deserialize] " + e.getMessage()); }
		return null;
	}
	
	public static Serializable deserialize(byte[] byteTab){
		try{
		    ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(byteTab));
		    Serializable exchange = (Serializable) input.readObject();
		    input.close();
		    return exchange;
		}catch (Exception e) { System.err.println("[Serializer.deserialize] " + e.getMessage()); }
		return null;
	}
	
	public static String serializeString(Serializable exchange){
		try{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        ObjectOutputStream output = new ObjectOutputStream( outputStream );
	        output.writeObject( exchange );
	        output.close();
	        return Base64.getEncoder().encodeToString(outputStream.toByteArray()); 
		}catch (Exception e) { System.err.println("[Serializer.deserialize] " + e.getMessage()); }
		return null;
	}
	
	public static Serializable deserializeString(String string){
		try{
			byte[] data = Base64.getDecoder().decode(string);
		    ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(data));
		    Serializable exchange = (Serializable) input.readObject();
		    input.close();
		    return exchange;
		}catch (Exception e) { System.err.println("[Serializer.deserialize] " + e.getMessage()); }
		return null;
	}
}
