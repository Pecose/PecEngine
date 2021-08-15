package rmiserver;

import java.io.Serializable;

public class NewInstance implements Serializable{

	private Serializable target;
	
	public Serializable getTarget() {
		return this.target;
	}

	public NewInstance(Serializable target) {
		this.target = target;
	}
}
