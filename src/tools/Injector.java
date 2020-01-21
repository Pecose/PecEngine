package tools;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
 
public class Injector {
 
	private Hashtable<String, Constructor<?>> classList = new Hashtable<String, Constructor<?>>();
	
	public Object construct(String name, Object p1){
		return this.construct(name, new Object[] {p1});
	}
	
	public Object construct(String name, Object p1, Object p2){
		return this.construct(name, new Object[] {p1, p2});
	}

	public Object construct(String name, Object p1, Object p2, Object p3){
		return this.construct(name, new Object[] {p1, p2, p3});
	}
	
	public Object construct(String name, Object p1, Object p2, Object p3, Object p4){
		return this.construct(name, new Object[] {p1, p2, p3, p4});
	}
	
	public Object construct(String name, Object p1, Object p2, Object p3, Object p4, Object p5){
		return this.construct(name, new Object[] {p1, p2, p3, p4, p5});
	}
	
	public Object construct(String name, Object[] parameters){
		for(Object p : parameters) {
			name += " " +p.getClass().getSimpleName();
		}
		System.out.println("["+name+"]");
		try {
			return classList.get(name).newInstance(parameters);
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
 
	public Injector(String configFileName){
		try {
			Scanner scanner = Files.loadScanner(configFileName);
			ArrayList<String> namesList = new ArrayList<String>();
			while(scanner.hasNext()){ namesList.add(scanner.next()); }
			for(String string : namesList){ 
				Class<?> klass = Class.forName(string);
				String[] interfaces = new String[klass.getInterfaces().length];
				
				for(int i = 0; i < klass.getInterfaces().length; i++){
					Class<?> inter = klass.getInterfaces()[i];
					String[] s = inter.getName().split("\\.");
					interfaces[i] = s[s.length-1];
				}
				
				Constructor<?>[] constructors = Class.forName(string).getDeclaredConstructors();
				
				for(Constructor<?> c : constructors) {
					String[] s = c.getName().split("\\.");
					String name = s[s.length-1];
					Class<?>[] parameters = c.getParameterTypes();
					
					for(Class<?> p : parameters) {
						String className = p.getSimpleName();
						if(className.equals("int")){
							className = "Integer";
						}else if(className.equals("int[]")){
							className = "Integer[]";
						}else if(className.equals("char")){
							className = "Character";
						}else if(className.equals("char[]")){
							className = "Character[]";
						}else if(className.equals("float")){
							className = "Float";
						}else if(className.equals("float[]")){
							className = "Float[]";
						}else if(className.equals("double")){
							className = "Double";
						}else if(className.equals("double[]")){
							className = "Double[]";
						}else if(className.equals("long")){
							className = "Long";
						}else if(className.equals("long[]")){
							className = "Long[]";
						}else if(className.equals("boolean")){
							className = "Boolean";
						}else if(className.equals("boolean[]")){
							className = "Boolean[]";
						}else if(className.equals("byte")){
							className = "Byte";
						}else if(className.equals("byte[]")){
							className = "Byte[]";
						}else if(className.equals("short")){
							className = "Short";
						}else if(className.equals("short[]")){
							className = "Short[]";
						}
						name += " " +className;

					}
					System.out.println("#"+name+"#");
					classList.put(name, c);	
				}
			}
		} catch(Exception e) { e.printStackTrace(); }
	}
 
}