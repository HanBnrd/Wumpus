package wumpus;

public class State {
	protected int holes[][] = new int[2][2];
	protected int treasure[] = new int[2];
	protected int wumpus[] = new int[2];
	protected int hero[] = new int[2];
	protected boolean arrow;
	
	public State(int holes[][], int treasure[], int wumpus[], int hero[], boolean arrow) {
		this.holes = holes;
		this.treasure = treasure;
		this.wumpus = wumpus;
		this.hero = hero;
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
