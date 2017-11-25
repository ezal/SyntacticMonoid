import java.util.Map;
import java.util.TreeMap;

class MonoidElem {
	String name;
	Map<State,State> elem;

	MonoidElem() {
		elem = new TreeMap<State,State>();
	}
	
	State get(State k) {
		return elem.get(k);
	}
	
	boolean containsKey(State k) {
		return elem.containsKey(k);
	}
	
	void put(State k, State v) {
		elem.put(k, v); 		
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MonoidElem)
			return elem.equals(((MonoidElem)obj).elem);
		else
			throw new RuntimeException("equals: arg is non MonoidElem object");	
	}

	@Override
	public int hashCode() {
		return elem.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
	
	public String toFullString() {
		return name + "<" + elem.toString() + ">";
	}

}
