package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.Scanner;

public class Saver{
	
	private String path;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public Saver(String name) throws Exception{
		path = System.getProperty("user.dir").toString() + "\\" + name;
		File file = new File(path);
		if(!file.exists()){ file.createNewFile(); }
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
	
	public Scanner getScanner(){
		try{ return new Scanner(new File(path));
		}catch(Exception e){ e.printStackTrace(); }
		return null;
	}
}
