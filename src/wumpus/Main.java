package wumpus;

public class Main {

	public static void main(String[] args) {
		Algo algo = new Algo();
		State s = new State();
		Problem pb = new Problem();
		String result = algo.run(pb, s);
		System.out.println(result);
		System.out.println(s.toString());
	}
}
