import java.util.Set;



// TODO: could we use a standard automata package, e.g. dk.brics.automaton?
public class Automaton {	
	String name;
	
	Set<State> states;
	Set<String> labels;
	
	State trapState;
	State initState;
	Set<State> finalStates;
	Set<Edge> transitions;
	
	public Automaton(String s) {
		name = s;
	}
}
