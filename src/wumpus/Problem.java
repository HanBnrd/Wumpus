package wumpus;

public class Problem {
	
	/**
	 * Makes a new state considering one action
	 * @param state the origin state
	 * @param act the action done
	 * @return a new state with a modification of the concerned attributes
	 */
	public State transition(State state, Actions act) {
		State next = new State(state);
		switch(act) {
		case ShootUp:
			if (next.wumpus[0] == next.hero[0] && next.wumpus[1]>next.hero[1]) {
				next.wumpus = null;
			}
			next.useArrow();
			break;
		case ShootDown:
			if (next.wumpus[0] == next.hero[0] && next.wumpus[1]<next.hero[1]) {
				next.wumpus = null;
			}
			next.useArrow();
			break;
		case ShootRight:
			if (next.wumpus[1] == next.hero[1] && next.wumpus[0]>next.hero[0]) {
				next.wumpus = null;
			}
			next.useArrow();
			break;
		case ShootLeft:
			if (next.wumpus[1] == next.hero[1] && next.wumpus[0]<next.hero[0]) {
				next.wumpus = null;
			}
			next.useArrow();
			break;
		case Up:
			if (next.hero[1] < 3) {
				next.hero[1] += 1;
			}
			break;
		case Down:
			if (next.hero[1] > 0) {
				next.hero[1] -= 1;
			}
			break;
		case Right:
			if (next.hero[0] < 3) {
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
