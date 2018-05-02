package wumpus;

public class Problem {
	
	public State transition(State state, Actions act) {
		State next = new State(state);
		switch(act) {
		case ShootUp:
			if (next.wumpus[0] == next.hero[0] && next.wumpus[1]>next.hero[1]) {
				next.wumpus = null;
			}
			next.arrow = false;
			break;
		case ShootDown:
			if (next.wumpus[0] == next.hero[0] && next.wumpus[1]<next.hero[1]) {
				next.wumpus = null;
			}
			next.arrow = false;
			break;
		case ShootRight:
			if (next.wumpus[1] == next.hero[1] && next.wumpus[0]>next.hero[0]) {
				next.wumpus = null;
			}
			next.arrow = false;
			break;
		case ShootLeft:
			if (next.wumpus[1] == next.hero[1] && next.wumpus[0]<next.hero[0]) {
				next.wumpus = null;
			}
			next.arrow = false;
			break;
		case Up:
			if (next.hero[1] < 4) {
				next.hero[1] += 1;
			}
			break;
		case Down:
			if (next.hero[1] > 0) {
				next.hero[1] -= 1;
			}
			break;
		case Right:
			if (next.hero[0] < 4) {
				next.hero[0] += 1;
			}
			break;
		case Left:
			if (next.hero[0] > 0) {
				next.hero[0] -= 1;
			}
			break;
		}
		return next;
	}
}
