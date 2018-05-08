package wumpus;

public class Main {

	public static void main(String[] args) {
		String[] result;
		int treasureTime = 0, totalWin = 0, totalHole = 0, totalWumpus = 0;
		for (int i = 0; i < 100000; i++) {
			Algo algo = new Algo();
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
		System.out.println("Number of wins : "+totalWin+", with a mean time of "+treasureTime/totalWin+
						   "\nNumber of deaths from holes : "+totalHole+
						   "\nNumber of deaths from Wumpus : "+totalWumpus);
		//System.out.println(result);
		//System.out.println(s.toString());
	}
}
