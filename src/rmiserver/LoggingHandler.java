package rmiserver;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LoggingHandler implements InvocationHandler{

	private final String name;
	private final ClientConnexion client;
	
	public LoggingHandler(String name, ClientConnexion client) {
		this.name = name;
		this.client = client;
	}

	@Override
	public Serializable invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(client != null) {
			String parameterTypes = Arrays.asList(method.getParameterTypes()).toString();
			return client.invoke(new MethodCall(name, method.getName(), parameterTypes, args));
		} return null;
	}

}
