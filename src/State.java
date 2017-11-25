
public class State implements Comparable<State> {
	String name;

	State(String s) {
		name = s;
	}
	
	@Override
	public boolean equals(Object arg) {
		if (arg instanceof State)
			return name.equals(((State)arg).name);
		else
			throw new RuntimeException("equals: arg is non State object");
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(State arg) {
		return name.compareTo(arg.name);
	}
}
