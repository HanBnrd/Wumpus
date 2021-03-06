package wumpus;

import java.util.Comparator;

public class StateComparator implements Comparator<State> {

	@Override
	public int compare(State s1, State s2) {
		if (s1.getCost() < s2.getCost())
			return -1;
		if (s1.getCost() > s2.getCost())
			return 1;
		return 0;
	}
}
