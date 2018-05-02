package wumpus;

public class Algo {

	protected ModelValues[][] model;
	protected boolean[][] visited;
	
	public Algo() {
		model = new ModelValues[4][4]; 
		for (int i = 0; i < 4; i=i+1) {
			for (int j = 0; j < 4; j=j+1) {
				model[i][j] = ModelValues.Safe;
			}
		}
		
		visited = new boolean[4][4]; // False by default
	}
	
}
