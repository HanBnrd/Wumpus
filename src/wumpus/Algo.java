package wumpus;

import java.util.PriorityQueue;


public class Algo {

	protected PriorityQueue<State> toVisit;
	protected boolean numerousThrows;
	protected Agent hero;

	/**
	 * Algo constructor
	 */
	public Algo(boolean severalThrows) {
		numerousThrows = severalThrows;
		
		hero = new Agent(this);

		toVisit = new PriorityQueue<State>(new StateComparator());
	}

	/**
	 * Main method of the Algo class, runs the implementation of the algorithm
	 * @return 2 string, the first is about how it ended, the second is the number of visited squares
	 */
	public String[] run(Problem pb, State init) {
		boolean end = false;
		boolean notAdded = false;
		int time = 0;
		String gameover = null;
		toVisit.add(init);

		while (!end) {
			State currentState = toVisit.poll();
			if(!isNumerousThrows())
				System.out.println("Hero position : (" + currentState.getHero()[0]+","+currentState.getHero()[1]+")");
			time ++;
			Observation obs = new Observation(currentState);
			getHero().setVisited(obs.getHeroPosition()[0], obs.getHeroPosition()[1]);
			
			if (currentState.isTreasure()) {
				gameover = "Treasure";
				end = true;
			}
			else if (currentState.isWumpus()) {
				gameover = "Wumpus";
				end = true;
			}
			else if (currentState.isHole()) {
				gameover = "Hole";
				end = true;
			}
			else {
				getHero().updateModel(obs);
				for (Actions act : Actions.values()) {
					State next = pb.transition(currentState, act);
					notAdded = true;
					for(State s: toVisit) {
						if(s.getHero()[0] == next.getHero()[0] && 
						   s.getHero()[1] == next.getHero()[1] &&
						   s.getCost() == next.getCost()) {
							notAdded = false;
						}
					}
					if (notAdded && getHero().visited[next.getHero()[0]][next.getHero()[1]] == false) {
						getHero().setCost(next);
						toVisit.add(next);
					}
				}
			}
			if(!isNumerousThrows())
				System.out.println(toString());
		}
		String game[] = {gameover,""+time};
		return game;
	}
	
	/**
	 * Getter of hero
	 * @return instance of hero
	 */
	public Agent getHero() {
		return this.hero;
	}
	
	/**
	 * Required when we do not want to print everything
	 * @return true if we want to skip printing, false otherwise
	 */
	public boolean isNumerousThrows() {
		return numerousThrows;
	}
	
	/**
	 * Getter of the queue where future states to visit are in
	 * @return the instance of queue
	 */
	public PriorityQueue<State> getQueue() {
		return toVisit;
	}

	public String toString() {
		int gridSize = State.GRIDSIZE;
		StringBuilder sb = new StringBuilder();
		sb.append("Model :\t\t\t\t\tVisited :\n");
		
		for (int i = 0; i < gridSize; i=i+1) {
			for (int j = 0; j < gridSize; j=j+1) {
				sb.append("" + getHero().getModelValue(j, i).name() + "\t");
			}
			sb.append("\t");
			for (int j = 0; j < gridSize; j=j+1) {
				sb.append("" + getHero().getVisitedValue(j, i) + "\t");
			}
			sb.append("\n");
		}
		sb.append("---------------------------------------------------------------------");
		return sb.toString();
	}
}
