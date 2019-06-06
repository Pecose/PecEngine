package pecose.billos.saving;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;

import pecose.billos.serializer.Serializer;

public class Saver{
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public Saver(String name) throws Exception{
		String path = System.getProperty("user.dir").toString() + "/src/pecose/billos/backup/" + name + ".txt";
		File f = new File(path);
		if(!f.exists()){ f.createNewFile(); }
		reader = new BufferedReader(new FileReader(path));
		writer = new BufferedWriter(new FileWriter(path));
	}
	
	public void save(Serializable save){
		try{
			writer.write(Serializer.serializeString(save));
			writer.flush();
		}catch(Exception e){
			System.err.println("[Saver save] " + e.getMessage());
		}
		
	}
	
	public Serializable load(){
		try{
			return Serializer.deserializeString(reader.readLine());
		}catch(Exception e){
			System.err.println("Saver load " + e.getMessage());
		}return null;
	}
}
