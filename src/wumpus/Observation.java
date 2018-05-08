package wumpus;

public class Observation {
	protected boolean air;
	protected boolean smell;
	protected int[] heroPosition;
	protected State state;
	
	/**
	 * Main Observation constructor
	 * @param s the state related to this observation
	 */
	public Observation(State s) {
		state = s;
		System.out.println("Is Wumpus dead ? " + (s.wumpus == null));
		if (s.wumpus != null &&
			((s.hero[0] == s.wumpus[0] && s.hero[1] == s.wumpus[1]+1) ||
			(s.hero[0] == s.wumpus[0] && s.hero[1] == s.wumpus[1]-1) ||
			(s.hero[0] == s.wumpus[0]+1 && s.hero[1] == s.wumpus[1]) ||
			(s.hero[0] == s.wumpus[0]-1 && s.hero[1] == s.wumpus[1]))) {
			this.smell = true;
		}
		else {
			this.smell = false;
		}
		if ((s.hero[0] == s.hole1[0]   && s.hero[1] == s.hole1[1]+1) ||
			(s.hero[0] == s.hole1[0]   && s.hero[1] == s.hole1[1]-1) ||
			(s.hero[0] == s.hole1[0]+1 && s.hero[1] == s.hole1[1]  ) ||
			(s.hero[0] == s.hole1[0]-1 && s.hero[1] == s.hole1[1]  ) ||
			(s.hero[0] == s.hole2[0]   && s.hero[1] == s.hole2[1]+1) ||
			(s.hero[0] == s.hole2[0]   && s.hero[1] == s.hole2[1]-1) ||
			(s.hero[0] == s.hole2[0]+1 && s.hero[1] == s.hole2[1]  ) ||
			(s.hero[0] == s.hole2[0]-1 && s.hero[1] == s.hole2[1]  )) {
			this.air = true;
		}
		else {
			this.air = false;
		}
		
		heroPosition = s.hero;
	}
	
	/**
	 * @return the hero position in an array
	 */
	public int[] getHeroPosition() {
		return heroPosition;
	}
	
	public State getState() {
		return state;
	}
	
	/**
	 * Gets the observations
	 * @return a boolean array of 2 values : if there is air, if there is smell
	 */
	public boolean[] getObservations() {
		boolean obs[] = {this.air, this.smell};
		return obs;
	}
}
