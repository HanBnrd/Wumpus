package wumpus;

public class Observation {
	protected boolean air;
	protected boolean smell;
	
	public Observation(State s) {
		if ((s.hero[0] == s.wumpus[0] && s.hero[1] == s.hero[1]+1) ||
			(s.hero[0] == s.wumpus[0] && s.hero[1] == s.hero[1]-1) ||
			(s.hero[0] == s.wumpus[0]+1 && s.hero[1] == s.hero[1]) ||
			(s.hero[0] == s.wumpus[0]-1 && s.hero[1] == s.hero[1])) {
			this.smell = true;
		}
		else {
			this.smell = false;
		}
		if ((s.hero[0] == s.holes[0][0] && s.hero[1] == s.holes[0][1]+1) ||
			(s.hero[0] == s.holes[0][0] && s.hero[1] == s.holes[0][1]-1) ||
			(s.hero[0] == s.holes[0][0]+1 && s.hero[1] == s.holes[0][1]) ||
			(s.hero[0] == s.holes[0][0]-1 && s.hero[1] == s.holes[0][1]) ||
			(s.hero[0] == s.holes[1][0] && s.hero[1] == s.holes[0][1]+1) ||
			(s.hero[0] == s.holes[1][0] && s.hero[1] == s.holes[0][1]-1) ||
			(s.hero[0] == s.holes[1][0]+1 && s.hero[1] == s.holes[0][1]) ||
			(s.hero[0] == s.holes[1][0]-1 && s.hero[1] == s.holes[0][1])) {
			this.air = true;
		}
		else {
			this.air = false;
		}
	}
	
	public boolean[] getObservations() {
		boolean obs[] = {this.air, this.smell};
		return obs;
	}
}
