package tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
 
public class Injector {
 
	private Hashtable<String, Object> classList = new Hashtable<String, Object>();
 
	public Object get(String string){ return classList.get(string); }
 
	@SuppressWarnings("resource")
	public Injector(String configFileName){
		try {
			Scanner scanner = new Scanner(new File(configFileName));
			ArrayList<String> namesList = new ArrayList<String>();
			while(scanner.hasNext()){ namesList.add(scanner.next()); }
			for(String string : namesList){ 
//				Object object = Class.forName(string).newInstance();
				Object object = Class.forName(string).getDeclaredConstructor();
				String[] name = object.getClass().getInterfaces()[0].getName().split("\\.");
				classList.put(name[name.length-1], object);	
 
			}
		} catch(Exception e) { e.printStackTrace(); }
	}
 
}