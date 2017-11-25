
public class Edge implements Comparable<Edge> {
	State src;
	String label;
	State dst;
	
	Edge(State s, String l, State d) {
		src = s;
		label = l;
		dst =d;
	}
	
	@Override
	public int compareTo(Edge o) {
		int i1 = src.compareTo(o.src);
		if (i1 == 0) {
			int i2 = label.compareTo(o.label);
			if (i2 == 0)
				return dst.compareTo(o.dst);
			else 
				return i2;
		}
		else 
			return i1;
	}
}
