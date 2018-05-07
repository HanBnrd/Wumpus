package wumpus;

import java.util.Random;

public class State {
	protected int hero[], wumpus[], hole1[], hole2[], treasure[];
	protected int cost;
	protected boolean arrow;
	protected Observation obs;
	
	/**
	 * Default, first state
	 */
	public State() {
		hero = new int[2]; // (0;0)
		treasure = new int[2];
		hole1 = new int[2];
		hole2 = new int[2];
		wumpus = new int[2];
		cost = 1;
		
		Random rnd = new Random(/*System.currentTimeMillis()*/);
		do {
			treasure[0] = rnd.nextInt(3);
			treasure[1] = rnd.nextInt(3);
		} while (treasure[0] == 0 && treasure[1] == 0);
		
		do {
			hole1[0] = rnd.nextInt(3);
			hole1[1] = rnd.nextInt(3);
		} while ((hole1[0] == 0 && hole1[1] == 0) ||
				(hole1[0] == treasure[0] && hole1[1] == treasure[1]));
		
		do {
			hole2[0] = rnd.nextInt(3);
			hole2[1] = rnd.nextInt(3);
		} while ((hole2[0] == 0 && hole2[1] == 0) ||
				(hole2[0] == treasure[0] && hole2[1] == treasure[1]) ||
				(hole2[0] == hole1[0] && hole2[1] == hole1[1]));
		
		do {
			wumpus[0] = rnd.nextInt(3);
			wumpus[1] = rnd.nextInt(3);
		} while ((wumpus[0] == 0 && wumpus[1] == 0) ||
				(wumpus[0] == treasure[0] && wumpus[1] == treasure[1]) ||
				(wumpus[0] == hole1[0] && wumpus[1] == hole1[1]) ||
				(wumpus[0] == hole2[0] && wumpus[1] == hole2[1]));
		
		this.arrow = true;
	}
	
	/******* Getters - setters *******/
	public int[] getHero() {
		return hero;
	}

	public void setHero(int[] hero) {
		this.hero = hero;
	}

	public boolean arrow() {
		return arrow;
	}

	public void useArrow() {
		this.arrow = false;
	}
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	
	/**
	 * Copy constructor
	 * @param s the state to copy
	 */
	public State(State s) {
		this.hole1 = s.hole1;
		this.hole2 = s.hole2;
		this.treasure = s.treasure;
		this.wumpus = s.wumpus;
		this.hero = s.hero.clone();
		this.arrow = s.arrow;
		this.cost = 999; //default cost
	}
	
	public Observation makeObservation() {
		return new Observation(this);
	}
	
	/**
	 * Checks if the state is final or not
	 * @return true if the hero is dead or if he has found the treasure, false otherwise
	 */
	public boolean isFinal() {
		boolean end = false;
		if (hero == treasure) {
			end = true;
		}
		else if (hero == wumpus) {
			end = true;
		}
		else {
			if (hero == hole1 || hero == hole2) {
				end = true;
			}
		}
		return end;
	}
}
