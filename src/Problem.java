
public class Problem {
	String[] actions = {"up", "down", "right", "left", "shootUp", "shootDown", "shootRight", "shootLeft"};

	public String[] getActions() {
		return this.actions;
	}
	
	public State transition(State state,String act) {
		State next = state.copy();
		// Shoot
		if (act == "shootUp") {
			if (next.wumpus[0] == next.hero[0] && next.wumpus[1]>next.hero[1]) {
				next.wumpus = null;
			}
			next.arrow = false;
		}
		else if (act == "shootDown") {
			if (next.wumpus[0] == next.hero[0] && next.wumpus[1]<next.hero[1]) {
				next.wumpus = null;
			}
			next.arrow = false;
		}
		else if (act == "shootRight") {
			if (next.wumpus[1] == next.hero[1] && next.wumpus[0]>next.hero[0]) {
				next.wumpus = null;
			}
			next.arrow = false;
		}
		else if (act == "shootLeft") {
			if (next.wumpus[1] == next.hero[1] && next.wumpus[0]<next.hero[0]) {
				next.wumpus = null;
			}
			next.arrow = false;
		}
		// Movement
		else if (act == "up") {
			if (next.hero[1] < 4) {
				next.hero[1] += 1;
			}
		}
		else if (act == "down") {
			if (next.hero[1] > 0) {
				next.hero[1] -= 1;
			}
		}
		else if (act == "right") {
			if (next.hero[0] < 4) {
				next.hero[0] += 1;
			}
		}
		else if (act == "left") {
			if (next.hero[0] > 0) {
				next.hero[0] -= 1;
			}
		}
		return next;
	}

}
