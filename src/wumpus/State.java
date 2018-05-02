package wumpus;

import java.util.Random;

public class State {
	protected int holes[][];
	protected int treasure[];
	protected int wumpus[];
	protected int hero[];
	protected boolean arrow;
	
	public State() {
		Random rnd = new Random();
		do {
			this.treasure[0] = rnd.nextInt(3);
			this.treasure[1] = rnd.nextInt(3);
		} while (treasure[0] == 0 && treasure[1] == 0);
		do {
			this.holes[0][0] = rnd.nextInt(3);
			this.holes[0][1] = rnd.nextInt(3);
		} while ((holes[0][0] == 0 && holes[0][1] == 0) ||
				(holes[0][0] == treasure[0] && holes[0][1] == treasure[1]));
		do {
			this.holes[1][0] = rnd.nextInt(3);
			this.holes[1][1] = rnd.nextInt(3);
		} while ((holes[1][0] == 0 && holes[1][1] == 0) ||
				(holes[1][0] == treasure[0] && holes[1][1] == treasure[1]) ||
				(holes[1][0] == holes[0][0] && holes[1][1] == holes[0][1]));
		do {
			this.wumpus[0] = rnd.nextInt(3);
			this.wumpus[1] = rnd.nextInt(3);
		} while ((wumpus[0] == 0 && wumpus[1] == 0) ||
				(wumpus[0] == treasure[0] && wumpus[1] == treasure[1]) ||
				(wumpus[0] == holes[0][0] && wumpus[1] == holes[0][1]) ||
				(wumpus[0] == holes[1][0] && wumpus[1] == holes[1][1]));
		this.arrow = true;
	}
	
	public State(State s) {
		this.holes = s.holes;
		this.treasure = s.treasure;
		this.wumpus = s.wumpus;
		this.hero = s.hero.clone();
		this.arrow = s.arrow;
	}
	
	public boolean isFinal() {
		boolean end = false;
		if (this.hero == this.treasure) {
			end = true ;
		}
		else if (hero == wumpus) {
			end = true ;
		}
		else {
			for (int i = 0; i < this.holes.length ; i++) {
				if (this.hero == this.holes[i]) {
					end = true;
				}
			}
		}
		return end;
	}
}
