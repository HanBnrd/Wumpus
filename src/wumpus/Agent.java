package wumpus;

import java.util.PriorityQueue;

public class Agent {

	protected ModelValues[][] model;
	protected boolean[][] visited;
	protected Algo algo;
	
	/**
	 * Constructor of Agent
	 * @param a the Algo instance which requires the agent
	 */
	public Agent(Algo a) {
		algo = a;
		int gridSize = State.GRIDSIZE;
		model = new ModelValues[gridSize][gridSize];
		for (int i = 0; i < gridSize; i=i+1) {
			for (int j = 0; j < gridSize; j=j+1) {
				model[i][j] = ModelValues.Unknown;
			}
		}
		model[0][0] = ModelValues.Safe;

		visited = new boolean[4][4]; // False by default
	}
	
	/**
	 * Method called when we understand the exact position of the Wumpus,
	 * kills him and clear our other predictions about the Wumpus, several squares become safer
	 * @param currentState
	 */
	protected void clearMaybeWumpus(State currentState) {
		PriorityQueue<State> toVisit = getAlgo().getQueue();
		for (int i = 0; i < State.GRIDSIZE; i=i+1) {
			for (int j = 0; j < State.GRIDSIZE; j=j+1) {
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
	 * Chooses the right value to put in one cell of the model, considering the new observations
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
	 * Updates the model with an observation of the surroundings
	 * @param o the observation
	 */
	protected void updateModel(Observation o) {
		int[] hPos = o.getHeroPosition();
		model[hPos[0]][hPos[1]] = ModelValues.Safe;

		if(hPos[0] > 0) {
			chooseModelValue(o, hPos[0]-1, hPos[1]);
		}

		if(hPos[0] < State.GRIDSIZE-1) {
			chooseModelValue(o, hPos[0]+1, hPos[1]);
		}

		if(hPos[1] > 0) {
			chooseModelValue(o, hPos[0], hPos[1]-1);
		}

		if(hPos[1] < State.GRIDSIZE-1) {
			chooseModelValue(o, hPos[0], hPos[1]+1);
		}
	}
	
	/**
	 * Sets the cost of a new state with respect to the model
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
	 * Sets the cell as visited
	 * @param x the cell abscissa
	 * @param y the cell ordinate
	 */
	protected void setVisited(int x, int y) {
		if(x >= 0 && x < State.GRIDSIZE) {
			if (y >= 0 && y < State.GRIDSIZE) {
				visited[x][y] = true;
			}
		}
	}
	
	/**
	 * Getter of algo
	 * @return the instance of algo
	 */
	public Algo getAlgo() {
		return this.algo;
	}
	
	/**
	 * Getter of one model square
	 * @param x the abscissa
	 * @param y the ordinate
	 * @return the value of the square
	 */
	public ModelValues getModelValue(int x, int y) {
		return model[x][y];
	}
	
	/**
	 * Getter of one square of the visited grid
	 * @param x the abscissa
	 * @param y the ordinate
	 * @return the value of the square
	 */
	public boolean getVisitedValue(int x, int y) {
		return visited[x][y];
	}
	
}
