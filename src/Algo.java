
public class Algo {

	protected ModelValues[][] modele;
	
	public Algo() {
		modele = new ModelValues[4][4]; 
		for (int i = 0; i < 4; i=i+1) {
			for (int j = 0; j < 4; j=j+1) {
				modele[i][j] = ModelValues.NotVisited;
			}
		}
		
	}
	
}
