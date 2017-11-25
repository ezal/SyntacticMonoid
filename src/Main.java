import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

public class Main {
	
	static Automaton taintsAut() {
		Automaton a = new Automaton("taintsAut");
		
		String lok = "ok"; // "does-not-taint";
		String lbad = "bad"; // "taints";
		
		a.labels = new TreeSet<String>();
		a.labels.add(lok);
		a.labels.add(lbad);
		
		State sU = new State("sU");
		State sT = new State("sT");
		a.states = new TreeSet<State>();
		a.states.add(sU);
		a.states.add(sT);
		
		a.initState = sU;
		a.trapState = sT;

		a.finalStates = new TreeSet<State>();
		a.finalStates.add(sU);
		
		Edge sUloop = new Edge(sU, lok, sU);
		a.transitions = new TreeSet<Edge>();
		a.transitions.add(sUloop);
		
		return a;
	}
	
	static Automaton xssAut() {
		Automaton a = new Automaton("xssAut");
		
		String llit = "lit"; 
		String linput = "input";
		String lc1 = "c1"; 
		String lc2 = "c2";
		String lscript = "script"; 
		String lendscript = "endScript";
		
		a.labels = new TreeSet<String>();
		a.labels.add(llit);
		a.labels.add(linput);
		a.labels.add(lc1);
		a.labels.add(lc2);
		a.labels.add(lscript);
		a.labels.add(lendscript);
		
		State sH = new State("sH"); // HTML
		State sS = new State("sS"); // SCRIPT
		State sT = new State("sT"); // FAIL
		a.states = new TreeSet<State>();
		a.states.add(sH);
		a.states.add(sS);
		a.states.add(sT);
		
		a.initState = sH;
		a.trapState = sT;

		a.finalStates = new TreeSet<State>();
		a.finalStates.add(sH);
		a.finalStates.add(sS);
		
		Edge sH2sS = new Edge(sH, lscript, sS);
		Edge sS2sH = new Edge(sS, lendscript, sH);
		Edge sHloopLit = new Edge(sH, llit, sH);
		Edge sHloopC1 = new Edge(sH, lc1, sH);
		Edge sHloopEndScript = new Edge(sH, lendscript, sH);
		Edge sSloopLit = new Edge(sS, llit, sS);
		Edge sSloopC2 = new Edge(sS, lc2, sS);
		Edge sSloopScript = new Edge(sS, lscript, sS);
		
		a.transitions = new TreeSet<Edge>();
		a.transitions.add(sH2sS);
		a.transitions.add(sS2sH);
		a.transitions.add(sHloopLit);
		a.transitions.add(sHloopC1);
		a.transitions.add(sHloopEndScript);
		a.transitions.add(sSloopLit);
		a.transitions.add(sSloopC2);
		a.transitions.add(sSloopScript);
		
		return a;
	}

	static Automaton authorizationAut() {
		Automaton a = new Automaton("authorizationAut");
		
		String lauthF = "authF"; 
		String lauthPh = "authPh";
		String laccF = "accF";
		String laccPh = "accPh";
		String lwthd = "wthd";		 
		
		a.labels = new TreeSet<String>();
		a.labels.addAll(Arrays.asList(lauthF, lauthPh, laccF, laccPh, lwthd));
		
		State sO = new State("sO");
		State sA = new State("sA");
		State sB = new State("sB");
		State sX = new State("sX");
		State sT = new State("sT");
		
		a.states = new TreeSet<State>();
		a.states.addAll(Arrays.asList(sO, sA, sB, sX, sT)); 
		
		a.initState = sO;
		a.trapState = sT;

		a.finalStates = new TreeSet<State>();
		a.finalStates.addAll(Arrays.asList(sO, sA, sB, sX));
		
		Edge eOA = new Edge(sO, lauthF, sA);
		Edge eAO = new Edge(sA, laccF, sO);
		Edge eOB = new Edge(sO, lauthPh, sB);
		Edge eBO = new Edge(sB, laccPh, sO);
		Edge eAX = new Edge(sA, lauthPh, sX);
		Edge eXA = new Edge(sX, laccPh, sA);
		Edge eBX = new Edge(sB, lauthF, sX);
		Edge eXB = new Edge(sX, laccF, sB);
		Edge eAOw = new Edge(sA, lwthd, sO);
		Edge eBOw = new Edge(sB, lwthd, sO);
		Edge eXOw = new Edge(sX, lwthd, sO);
		
		a.transitions = new TreeSet<Edge>();
		a.transitions.addAll(Arrays.asList(eOA, eAO, eOB, eBO, eAX, eXA, eBX, eXB, eAOw, eBOw, eXOw));
		
		return a;
	}
	
	static Automaton reqAut() {
		Automaton a = new Automaton("reqAut");
		
		String lreq = "req"; // "request";
		String luse = "use"; // "use";
		
		a.labels = new TreeSet<String>();
		a.labels.add(lreq);
		a.labels.add(luse);
		
		a.states = new TreeSet<State>();
		State s1 = new State("s1");
		State s2 = new State("s2");
		State s3 = new State("s3");		
		a.states.add(s1);
		a.states.add(s2);
		a.states.add(s3);
		
		a.initState = s1;

		a.finalStates = new TreeSet<State>();
		a.finalStates.add(s1);
		a.finalStates.add(s2);
		
		a.transitions = new TreeSet<Edge>();
		a.transitions.add(new Edge(s1, lreq, s2));
		a.transitions.add(new Edge(s1, luse, s3));
		a.transitions.add(new Edge(s2, lreq, s2));
		a.transitions.add(new Edge(s2, luse, s2));
		a.transitions.add(new Edge(s3, lreq, s3));
		a.transitions.add(new Edge(s3, luse, s3));
		
		return a;
	}

	public static void main(String[] args) {
		// Automaton a = taintsAut();
		// Automaton a = xssAut();
		// Automaton a = authorizationAut();
		Automaton a = reqAut();
		
		SyntacticMonoid mon = new SyntacticMonoid(a);

		try {
			PrintWriter writer = new PrintWriter("examples/" + a.name + ".txt");
			mon.outputMonoid(writer);    
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
