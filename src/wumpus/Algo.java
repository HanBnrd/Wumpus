package wumpus;

import java.util.PriorityQueue;


public class Algo {

	protected ModelValues[][] model;
	protected boolean[][] visited;
	protected int gridSize;
	protected PriorityQueue<State> toVisit;

	/**
	 * Algo constructor
	 * Grid of 4x4 by default
	 * Start position of (0;0) by default
	 */
	public Algo() {
		gridSize = 4;

		model = new ModelValues[gridSize][gridSize];
		for (int i = 0; i < gridSize; i=i+1) {
			for (int j = 0; j < gridSize; j=j+1) {
				model[i][j] = ModelValues.Unknown;
			}
		}
		model[0][0] = ModelValues.Safe;

		visited = new boolean[4][4]; // False by default

		toVisit = new PriorityQueue<State>(new StateComparator());
	}

	/**
	 * Main method of the Algo class
	 * @return
	 */
	public String[] run(Problem pb, State init) {
		boolean end = false;
		boolean notAdded = false;
		int time = 0;
		String gameover = null;
		toVisit.add(init);

		while (!end) {
			State currentState = toVisit.poll();
			//System.out.println("Hero position : (" + currentState.getHero()[0]+","+currentState.getHero()[1]+")");
			time ++;
			Observation obs = currentState.makeObservation();
			setVisited(obs.getHeroPosition()[0], obs.getHeroPosition()[1]);
			
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
				updateModel(obs);
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
					if (notAdded && visited[next.getHero()[0]][next.getHero()[1]] == false) {
						this.setCost(next);
						toVisit.add(next);
					}
				}
			}
			//System.out.println(toString());
		}
		String game[] = {gameover,""+time};
		return game;
	}

	/**
	 * Get the grid size
	 * @return
	 */
	public int getGSize() {
		return this.gridSize;
	}
	
	protected void clearMaybeWumpus(State currentState) {
		for (int i = 0; i < gridSize; i=i+1) {
			for (int j = 0; j < gridSize; j=j+1) {
				if(model[i][j] == ModelValues.MaybeW) {
					model[i][j] = ModelValues.Safe;
					State safeState = new State(currentState);
					safeState.setHero(i, j);
					setCost(safeState);
					toVisit.add(safeState);
				} else if (model[i][j] == ModelValues.MaybeWH) {
					model[i][j] = ModelValues.MaybeH;
					State maybeSafeState = new State(currentState);
					maybeSafeState.setHero(i, j);
					setCost(maybeSafeState);
					toVisit.add(maybeSafeState);
				}
			}
		}
		currentState.killWumpus();
		for (State s: toVisit) {
			s.killWumpus();
		}
	}

	/**
	 * Choose the right value to put in one cell of the model, considering the new observations
	 * @param o the new observations
	 * @param posX the cell abscissa
	 * @param posY the cell ordinate
	 * @return the accurate value
	 */
	protected void chooseModelValue(Observation o, int posX, int posY) {
		if(model[posX][posY] != ModelValues.Safe) {
		
			boolean[] results = o.getObservations();
				
			if(results[0] && results[1]) {
				if(model[posX][posY] == ModelValues.MaybeW || model[posX][posY] == ModelValues.MaybeWH) {
					clearMaybeWumpus(o.getState());
				} else {
					model[posX][posY] = ModelValues.MaybeWH;
				}
			} else if(results[0]) {
				model[posX][posY] = ModelValues.MaybeH;
			} else if(results[1]) {
				if(model[posX][posY] == ModelValues.MaybeW || model[posX][posY] == ModelValues.MaybeWH) {
					clearMaybeWumpus(o.getState());
				} else {
					model[posX][posY] = ModelValues.MaybeW;
				}
			} else {
				model[posX][posY] = ModelValues.Safe;
			}
		}
	}

	/**
	 * Update the model with an observation of the surroundings
	 * @param o the observation
	 */
	protected void updateModel(Observation o) {
		int[] hPos = o.getHeroPosition();
		model[hPos[0]][hPos[1]] = ModelValues.Safe;

		if(hPos[0] > 0) {
			chooseModelValue(o, hPos[0]-1, hPos[1]);
		}

		if(hPos[0] < getGSize()-1) {
			chooseModelValue(o, hPos[0]+1, hPos[1]);
		}

		if(hPos[1] > 0) {
			chooseModelValue(o, hPos[0], hPos[1]-1);
		}

		if(hPos[1] < getGSize()-1) {
			chooseModelValue(o, hPos[0], hPos[1]+1);
		}
	}
	
	/**
	 * Set the cost of a new state with respect to the model
	 * @param newState the new state
	 */
	protected void setCost(State newState) {
		int cost = 999;
		int [] posHero = newState.getHero();
		ModelValues mv = model[posHero[0]][posHero[1]];
		switch (mv) {
		case Safe:
			cost = 1;
			break;
		case MaybeW:
			cost = 5;
			break;
		case MaybeH:
			cost = 10;
			break;
		case MaybeWH:
			cost = 20;
			break;
		default:
			cost = 666; //Death
			break;
		}
		newState.setCost(cost);
	}	
	
	/**
	 * Set the cell as visited
	 * @param x the cell abscissa
	 * @param y the cell ordinate
	 */
	protected void setVisited(int x, int y) {
		if(x >= 0 && x < getGSize()) {
			if (y >= 0 && y < getGSize()) {
				visited[x][y] = true;
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Model :\t\t\t\t\tVisited :\n");
		for (int i = 0; i < gridSize; i=i+1) {
			for (int j = 0; j < gridSize; j=j+1) {
				sb.append("" + model[j][i].name() + "\t");
			}
			sb.append("\t");
			for (int j = 0; j < gridSize; j=j+1) {
				sb.append("" + visited[j][i] + "\t");
			}
			sb.append("\n");
		}
		sb.append("---------------------------------------------------------------------");
		return sb.toString();
	}
	
	/**
	 * @deprecated
	 * @return
	 */
	public String oldToString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Visited : \n");
		for (int i = 0; i < gridSize; i=i+1) {
			for (int j = 0; j < gridSize; j=j+1) {
				if (visited[j][i]) {
					sb.append("X");
				} else {
					sb.append("-");
				}
			}
			sb.append("\n");
		}

		sb.append("\nModel :\n");

		for (int i = 0; i < gridSize; i=i+1) {
			for (int j = 0; j < gridSize; j=j+1) {
				sb.append("" + model[j][i].name() + "\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
