package A;

public class Aaa implements Bbb{
	public int i = 0;
	public Aaa() {
		this.i = 1;
	}
	public Aaa(int i) {
		this.i = i;
	}
	@Override
	public int getI() {
		return i;
	}

}
