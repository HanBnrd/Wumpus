package wumpus;

public class Main {

	protected static int NBTHROWS = 100000;
	
	public Main (boolean severalThrows) {
		if(severalThrows) {
			String[] result;
			int treasureTime = 0, totalWin = 0, totalHole = 0, totalWumpus = 0;
			for (int i = 0; i < NBTHROWS; i++) {
				Algo algo = new Algo(severalThrows);
				State s = new State();
				Problem pb = new Problem();
				result = algo.run(pb, s);
				if(result[0].equals("Treasure")) {
					treasureTime += Integer.parseInt(result[1]);
					totalWin ++;
				} else if(result[0].equals("Wumpus")) {
					totalWumpus++;
				} else {
					totalHole++;
				}
			}
			System.out.println("Number of throws : "+NBTHROWS+ 
							   "\nNumber of wins : "+totalWin+", with a mean number of visited squares of "+treasureTime/totalWin+
							   "\nNumber of deaths from holes : "+totalHole+
							   "\nNumber of deaths from Wumpus : "+totalWumpus);
		} else {
			Algo algo = new Algo(severalThrows);
			State s = new State();
			System.out.println("Initial state\n" + s.toString()+"\n");
			Problem pb = new Problem();
			String[] result = algo.run(pb, s);
			if(result[0] == "Hole")
				System.out.println("Death in a hole");
			else if (result[0] == "Wumpus")
				System.out.println("Death by Wumpus");
			else
				System.out.println("Win with "+result[1]+" visited squares !");
		}
	}
	
	
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Use :\n \"java -jar wumpus.jar y\" for several throws\n \"java -jar wumpus.jar n\" for one detailed throw");
		} else {
			//false for one throw, true for 100,000 throws (it also changes the printed values)
			boolean numerousThrows;
			if(args[0].equals("y"))
				numerousThrows = true;
			else
				numerousThrows = false;
			new Main(numerousThrows);
		}
	}
}
