package wumpus;

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
	 */
	public void run() {
		State state = new State();
		//while(!state.isFinal()) {
			Observation obs = state.makeObservation();
			setVisited(obs.getHeroPosition()[0], obs.getHeroPosition()[1]);
			updateModel(obs);
			System.out.println(toString());
		//}
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
	 * Update the model with an observation of the surroudings
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
