package tools;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Injector {
	
	private Hashtable<String, Object> classList = new Hashtable<String, Object>();
	public Object get(String string){ return classList.get(string); }
	
	public Injector(String configFileName){
		try {
			Saver saver = new Saver("config.txt");
			Scanner scanner = saver.getScanner();
			ArrayList<String> namesList = new ArrayList<String>();
			while(scanner.hasNext()){ namesList.add(scanner.next()); }
			for(String string : namesList){ 
				Object object = Class.forName(string).newInstance();
				String[] name = object.getClass().getInterfaces()[0].getName().split("\\.");
				classList.put(name[name.length-1], object);	
				
			}
		} catch(Exception e) { e.printStackTrace(); }
	}

}
