import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;







public class SyntacticMonoid  {
	Automaton a;

	// we see a monoid element can be seen a (total) function from states to states
	// see the MonoidElem class
	
	Set<MonoidElem> elems;    // all elements
	Set<MonoidElem> accElems; // the "accepting" elements
	MonoidElem neutralElem;   // the neutral element
	
	Map<Pair<MonoidElem, MonoidElem>, MonoidElem> table; // the multiplication table

	
	// we assume that the domain of both mappings is the whole set of states
	MonoidElem compose(MonoidElem m1, MonoidElem m2) {
		MonoidElem res = new MonoidElem();
		for (State s: a.states) {
			res.put(s, m2.get(m1.get(s)));
		}
		return res;
	}
		
	String tfName(String x) {
		String firstLetter = x.substring(0, 1);
		String fl = firstLetter.toUpperCase();
		String rest = x.substring(1, x.length());
		return fl + rest;		
	}
	
	MonoidElem find(Set<MonoidElem> elements, MonoidElem e) {
		for (MonoidElem ee: elements)
			if (e.equals(ee))
				return ee;
		return null;
	}
	
	SyntacticMonoid(Automaton aut) {
		a = aut;
		
		elems = new HashSet<MonoidElem>();
		accElems = new HashSet<MonoidElem>();
		neutralElem = new MonoidElem();
		neutralElem.name = "Id";
		table = new HashMap<Pair<MonoidElem, MonoidElem>, MonoidElem>();
		
		
		// we create the first elements, one for each label
		for (String l: a.labels) {
			// System.out.println("for label " + l);
			MonoidElem ml = new MonoidElem();
			ml.name = tfName(l);
			for (Edge e: a.transitions) {
				if (l.equals(e.label))
					ml.put(e.src, e.dst);
			}
			for (State s: a.states) {
				// System.out.println("for state " + s);			
				if (!ml.containsKey(s)) {
					// System.out.println("map does not contain " + s);
					ml.put(s, a.trapState);
				}
			}
			elems.add(ml);
		}
		
		Set<MonoidElem> newElems = new HashSet<MonoidElem>(elems);
		// System.out.println(newElems);
		do {
			elems.addAll(newElems);
			for (MonoidElem m1: elems) {
				for (MonoidElem m2: elems) {
					MonoidElem newElem = compose(m1, m2);
					newElem.name = m1.name + m2.name;
					if (newElems.contains(newElem)) {
						MonoidElem existing = find(newElems, newElem);
						Pair<MonoidElem, MonoidElem> pair = new Pair<MonoidElem, MonoidElem>(m1, m2);
						if (table.containsKey(pair)) {
							MonoidElem res = table.get(pair);
							if (!res.equals(existing))
								throw new RuntimeException("internal error");
						}
						else
							table.put(pair, existing);
					}
					else {
						newElems.add(newElem);
						table.put(new Pair<MonoidElem, MonoidElem>(m1, m2), newElem);
					}
				}
			}
			// System.out.println(newElems);
		} while (!newElems.equals(elems));
		
		// find the neutral element
		for (State s: a.states)
			neutralElem.put(s, s);
		MonoidElem idElem = find(elems, neutralElem);
		if (idElem == null)
			elems.add(neutralElem);		
			// throw new RuntimeException("internal error");
		else
			neutralElem = idElem;
		
		// find the accepting elements
		// these are all elements that map the initial state to a final state
		for (MonoidElem e: elems) {
			if (e == null)
				System.out.println("makes no sense");
			State s = e.get(a.initState);
			if (s == null)
				System.out.println("error at: " + e.toFullString());
			else
				if (a.finalStates.contains(s))
					accElems.add(e);
		}
	}
	
	
	void outputMonoid(PrintWriter writer) {
		for (MonoidElem e: elems)
			writer.print(e + ", ");
		writer.println(";");
			
		writer.println("public Monoid neutralElement() { return " + neutralElem + "; }");
		
		for (MonoidElem acc: accElems)
			writer.println("allowed.add(" + acc + ");");
		
		for (Entry<Pair<MonoidElem, MonoidElem>, MonoidElem> entry: table.entrySet()) {
			Pair<MonoidElem, MonoidElem> pair = entry.getKey();
			MonoidElem m1 = pair.first;
			MonoidElem m2 = pair.second;
			MonoidElem res = entry.getValue();
			writer.println("table.put(new Pair<Monoid, Monoid>(" + m1.name + ", " + m2.name + "), " + res.name + ");");
		}
	}
}
