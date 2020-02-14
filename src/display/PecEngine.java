package display;

public interface PecEngine extends Creation, Display{
	
	public static void start(PecEngine pecEngine){ new Panel(pecEngine); }
	
}