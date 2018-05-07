package wumpus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Algo {

	protected ModelValues[][] model;
	protected boolean[][] visited;
	protected int gridSize;

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
	}

	/**
	 * Main method of the Algo class
	 * @return
	 */
	public ArrayList<Actions> run(Problem pb, State init) {
		boolean end = false;
		// TODO : map pas forcément utile...
		// TODO : à voir ce qu'on return
		Map<State, ArrayList<Actions>> dict = new HashMap<State, ArrayList<Actions>>();
		LinkedList<State> stack = new LinkedList<>();
		ArrayList<Actions> initPath = new ArrayList<Actions>();
		State finalState = null;

		dict.put(init, initPath);
		stack.add(init);

		while (!end) {
			State currentState = stack.pop();
			System.out.println(currentState); // TODO

			Observation obs = currentState.makeObservation();
			setVisited(obs.getHeroPosition()[0], obs.getHeroPosition()[1]);
			updateModel(obs);

			if (currentState.isFinal()) {
				finalState = currentState;
				end = true;
			}
			else {
				// Safe moves
				boolean anyNextSafeNotVisited = false;
				for (Actions act : Actions.values()) {
					State next = pb.transition(currentState, act);
					if (model[next.hero[0]][next.hero[1]] == ModelValues.Safe &&
					visited[next.hero[0]][next.hero[1]] == false) {
						ArrayList<Actions> currentPath = new ArrayList<Actions>(dict.get(currentState));
						currentPath.add(act);
						dict.put(next,currentPath);
						stack.add(next);
						anyNextSafeNotVisited = true;
					}
				}
				if (anyNextSafeNotVisited == false && stack.isEmpty() == false) {
				// TODO : attention ne marche que si on rebrousse chemin d'une case
				// TODO : il faudrait gérer le fait de vraiment se déplacer vers la case cible
					for (Actions act : Actions.values()) {
						State next = pb.transition(currentState, act);
						if (model[next.hero[0]][next.hero[1]] == ModelValues.Safe) {
							ArrayList<Actions> currentPath = new ArrayList<Actions>(dict.get(currentState));
							currentPath.add(act);
							dict.put(next,currentPath);
							stack.add(next);
						}
					}
				}
				else {
					// Risky moves
					boolean anyRisky = false;
					for (Actions act : Actions.values()) {
						State next = pb.transition(currentState, act);
						if (model[next.hero[0]][next.hero[1]] == ModelValues.MaybeW ||
						model[next.hero[0]][next.hero[1]] == ModelValues.MaybeH ||
						model[next.hero[0]][next.hero[1]] == ModelValues.MaybeWH) {
						// TODO : peut-être ordonner le choix des maybe (W > H > WH) ?
							ArrayList<Actions> currentPath = new ArrayList<Actions>(dict.get(currentState));
							currentPath.add(act);
							dict.put(next,currentPath);
							stack.add(next);
							anyRisky = true;
						}
					}
					// TODO : cas où on rebrousse chemin mais qu'on est finalement bloqué
					// TODO : il faudrait rerebrousser chemin pour voir si il y avait des maybe ?
					if (anyRisky == false && ) {

					}
					// Only game over moves left
					else {
						end = true;
					}
				}

			}
		}
		return dict.get(finalState);

	}

	/**
	 * Get the grid size
	 * @return
	 */
	public int getGSize() {
		return this.gridSize;
	}

	/**
	 * Choose the right value to put in one cell of the model, considering the new observations
	 * @param o the new observations
	 * @param posX the cell abscissa
	 * @param posY the cell ordinate
	 * @return the accurate value
	 */
	protected ModelValues chooseModelValue(Observation o, int posX, int posY) {
		// Definitive values
		if(model[posX][posY] == ModelValues.Safe) {
			return ModelValues.Safe;
		}
		if(model[posX][posY] == ModelValues.Wumpus) {
			return ModelValues.Wumpus;
		}
		if(model[posX][posY] == ModelValues.Hole) {
			return ModelValues.Hole;
		}
		
		boolean[] results = o.getObservations();
		if(results[0] || results[1]) {
			boolean corner = false;
			int[] heroPos = o.getHeroPosition();
			//if the hero is in one corner
			if((heroPos[0] == 0 && heroPos[1] == 0) ||
			   (heroPos[0] == 0 && heroPos[1] == getGSize()-1) ||
			   (heroPos[0] == getGSize()-1 && heroPos[1] == 0) ||
			   (heroPos[0] == getGSize()-1 && heroPos[1] == getGSize()-1)) {
				corner = true;
			}
			
			if(results[0] && results[1]) {
				if(corner) {
					return ModelValues.WOrH;
				}
				if(model[posX][posY] == ModelValues.MaybeW || model[posX][posY] == ModelValues.MaybeWH) {
					return ModelValues.Wumpus;
				}
				return ModelValues.MaybeWH;
			}
			if(results[0]) {
				if(corner) {
					//todo
				}
				return ModelValues.MaybeH;
			}
			//results[1] = true
			if(model[posX][posY] == ModelValues.MaybeW || model[posX][posY] == ModelValues.MaybeWH) {
				return ModelValues.Wumpus;
			}
			return ModelValues.MaybeW;
		}
		return ModelValues.Safe;
	}

	/**
	 * Update the model with an observation of the surroundings
	 * @param o the observation
	 */
	protected void updateModel(Observation o) {
		int[] hPos = o.getHeroPosition();

		if(hPos[0] > 0) {
			model[hPos[0]-1][hPos[1]] = chooseModelValue(o, hPos[0]-1, hPos[1]);
		}

		if(hPos[0] < getGSize()-1) {
			model[hPos[0]+1][hPos[1]] = chooseModelValue(o, hPos[0]+1, hPos[1]);
		}

		if(hPos[1] > 0) {
			model[hPos[0]][hPos[1]-1] = chooseModelValue(o, hPos[0], hPos[1]-1);
		}

		if(hPos[1] < getGSize()-1) {
			model[hPos[0]][hPos[1]+1] = chooseModelValue(o, hPos[0], hPos[1]+1);
		}
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
		sb.append("Visited : \n");
		for (int i = 0; i < gridSize; i=i+1) {
			for (int j = 0; j < gridSize; j=j+1) {
				if (visited[i][j]) {
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
				sb.append("" + model[i][j].name() + "\t");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}
