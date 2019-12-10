package display;

public interface PecEngine extends Display, Creation{
	
	public static void start(PecEngine pecEngine){ new Panel(pecEngine); }
	
}