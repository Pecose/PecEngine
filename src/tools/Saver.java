package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Saver{
	
	private String name;
	
	public Saver(String name){
		this.name = name; 
		Files.loadFile(name);
	}
	
	public void save(List<Serializable> list){
		BufferedWriter writer = Files.loadBufferedWriter(name, Files.REWRITE);
		try{
			for(Serializable data : list){
				writer.write(Serializer.serializeString(data));
				writer.write("\n");
			}
			writer.flush();
			writer.close();
		}catch(Exception e){ System.err.println("[Saver save] " + e.getMessage()); }
		
	}
	
	public List<Serializable> load(){
		BufferedReader reader = Files.loadBufferedReader(name);
		List<Serializable> list = new ArrayList<>();
		try{
			String line="";
			while ((line = reader.readLine()) != null) {
				list.add(Serializer.deserializeString(line));
			}
			reader.close();
		}catch(Exception e){ System.err.println("[Saver load] " + e.getMessage()); }
		return list;
	}
	
	public void saveStrings(List<String> list){
		BufferedWriter writer = Files.loadBufferedWriter(name, Files.REWRITE);
		try{
			for(String data : list){
				writer.write(data);
				writer.write("\n");
			}
			writer.flush();
			writer.close();
		}catch(Exception e){ System.err.println("[Saver save] " + e.getMessage()); }
	}
	
	public List<String> loadStrings(){
		BufferedReader reader = Files.loadBufferedReader(name);
		List<String> lignes = new ArrayList<>();
		try{
			String line="";
			while ((line = reader.readLine()) != null) {
				lignes.add(line);
			}
			reader.close();
		}catch(Exception e){ System.err.println("[Saver load strings] " + e.getMessage()); }
		return lignes;
	}
}
