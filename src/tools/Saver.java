package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Saver{
	
	private BufferedReader reader;
	private BufferedWriter writer;
	
	public Saver(String name){
		Files.loadFile(name);
		reader = Files.loadBufferedReader(name);
		writer = Files.loadBufferedWriter(name, Files.REWRITE);
	}
	
	public void save(List<Serializable> list){
		try{
			for(Serializable data : list){
				writer.write(Serializer.serializeString(data));
				writer.write("\n");
			}
			writer.flush();
		}catch(Exception e){
			System.err.println("[Saver save] " + e.getMessage());
		}
		
	}
	
	public List<Serializable> load(){
		List<Serializable> list = new ArrayList<>();
		try{
			String line="";
			while ((line = reader.readLine()) != null) {
				list.add(Serializer.deserializeString(line));
			}
		}catch(Exception e){
			System.err.println("[Saver load] " + e.getMessage());
		}
		return list;
	}
	
	public void saveStrings(List<String> list){
		try{
			for(String data : list){
				writer.write(data);
				writer.write("\n");
			}
			writer.flush();
		}catch(Exception e){ System.err.println("[Saver save] " + e.getMessage()); }
		
	}
	
	public List<String> loadStrings(){
		List<String> lignes = new ArrayList<>();
		try{
			String line="";
			while ((line = reader.readLine()) != null) {
				lignes.add(line);
			}
		}catch(Exception e){ System.err.println("[Saver load strings] " + e.getMessage()); }
		return lignes;
	}
	
	public void close() {
		this.closeReader();
		this.closeWriter();
	}
	public void closeReader() {
		try{
			reader.close();
		}catch(IOException e){ e.printStackTrace(); }
	}
	public void closeWriter() {
		try{
			writer.close();
		}catch(IOException e){ e.printStackTrace(); }
	}
	
}
