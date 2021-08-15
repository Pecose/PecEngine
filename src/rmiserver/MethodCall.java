package rmiserver;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodCall implements Serializable{
	
	private final String name;
	private final String methodName;
	private final String parameterTypes;
	private final Object[] args;
	
	public String getName() {
		return this.name;
	}
	
	public MethodCall(String name, String methodName, String parameterTypes, Object[] args) {
		this.name = name;
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.args = args;
	}
	
	public Serializable invokeMethod(Serializable s) {
		try {
//			p.getClass().getDeclaredMethod("display").getParameterTypes().toString();
//			Ajouter getParameterTypes à MethodCall pour pouvoir interpréter la surcharge
			
			for(Method m : s.getClass().getInterfaces()[0].getDeclaredMethods()) {
				String parameterTypes = Arrays.asList(m.getParameterTypes()).toString();
				if(m.getName().equals(this.methodName) && parameterTypes.equals(this.parameterTypes)) {
					return (Serializable) m.invoke(s, args);
				}
			}
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
}
