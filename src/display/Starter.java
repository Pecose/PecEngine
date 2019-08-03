package display;

public class Starter {
	
	protected static PecEngine pecEngine;
	protected static AddListener addListener;
	
	public static PecEngine getPecEngine(){ return pecEngine; }
	
	public static void addListener(AddListener addListener){ Starter.addListener = addListener; }
	public static void start(PecEngine pecEngine){ Starter.pecEngine = pecEngine; new Window(); }
	
}
