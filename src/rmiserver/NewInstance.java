package rmiserver;

import java.io.Serializable;

public class NewInstance implements Serializable{

	private Serializable target;
	private String name;
	
	public Serializable getTarget() { return this.target; }
	public String getName() { return this.name; }

	public NewInstance(Serializable target, String name) {
		this.target = target;
		this.name = name;
	}
}
