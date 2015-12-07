package knapsack;

import java.util.*;

public class Solver implements SolverInterface {
	
	public Solution solve(Instance instance) {
		Solution best = new Solution(instance);
		return best;
	}
}