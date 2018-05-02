
public class State {
	int holes[][] = new int[3][2];
	int treasure[] = new int[2];
	int wumpus[] = new int[2];
	int hero[] = new int[2];
	boolean arrow;
	
	public State(int holes[][], int treasure[], int wumpus[], int hero[], boolean arrow) {
		this.holes = holes;
		this.treasure = treasure;
		this.wumpus = wumpus;
		this.hero = hero;
		this.arrow = true;
	}
	
	public State copy() {
		State copy = new State(this.holes, this.treasure, this.wumpus, this.hero, this.arrow);
		return copy;
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
			for (int i = 0; i <3; i++) {
				if (this.hero == this.holes[i]) {
					end = true;
				}
			}
		}
		return end;
	}
}
