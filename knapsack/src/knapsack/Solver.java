package knapsack;

import java.util.*;

public class Solver implements SolverInterface {

	private int weight_limit = 0;
	private int amount = 0;
	Solution[][][] table; //saves instances of solution for easier recursion without the need
	//of using backtracking to trace the objects that will be taken
	Instance instance; // used to properly reduce weight in recursion
	
	public Solver() {}
	
	/**
	 * Solves the binary Knapsack by using dynamic programming
	 */
	@Override
	public Solution solve(Instance instance) {
		weight_limit = instance.getWeightLimit();
		amount = instance.getSize();
		this.instance = instance;
		
		table = new Solution[weight_limit + 1][amount][weight_limit + 1];
		
		for(int i = 0; i < weight_limit + 1; i++) {
			for(int j = 0; j < amount; j++) {
				for(int k = 0; k < weight_limit + 1; k++) {
					table[i][j][k] = new Solution(instance);
				}
			}
		}
		Solution start = new Solution(instance);
		Solution sol = recursion(weight_limit, 0, start, 0);
		

		return sol;
	}
	
	/**
	 * Recursive part of dynamic programming
	 * @param curr_volume Usable volume
	 * @param index position of item
	 * @param sol previous solution to use the recursion on
	 * @return solution with highest amount of value that is still feasible
	 */
	private Solution recursion(int curr_volume, int index, Solution sol, int packed) {
		if(index < amount) { // checks if every item has been taken into consideration
			if(table[curr_volume][index][packed].getValue() != 0) { //checks if cell is empty
				return table[curr_volume][index][packed]; // if not return calculated solution
			}
			
			ArrayList<Solution> list = new ArrayList<Solution>();
			
			for(int i = 0; i <= curr_volume/instance.getWeight(index); i++) {
				if(i == 0) {
					Solution a = recursion(curr_volume, index + 1, new Solution(sol), 0);
					list.add(a);
				} else {
					if(curr_volume - instance.getWeight(index) * i >= 0) {
						Solution a = recursion(curr_volume - instance.getWeight(index) * i,
								index + 1, new Solution(sol), i);
						list.add(a);
					}
				}
			}
			//recursion without item[index] taken
			/*
			Solution a = recursion(curr_volume, index + 1, new Solution(sol));
			
			//recursion with item[index] taken
			Solution b = new Solution(sol);
			//since we are calculating the rest of the tree we dont need to set b[index] to 1
			if(curr_volume - instance.getWeight(index) >= 0) {
				b = new Solution(recursion(curr_volume - instance.getWeight(index),
						index + 1, new Solution(b)));
			}
			//set b[index] to 1 in order to compare values of a and b
			b.set(index, 1);
			if(!b.isFeasible()){
				b.set(index, 0);
			}
			
			//save higher value of a and b into cell
			if(a.getValue() > b.getValue()) {
				table[curr_volume][index] = new Solution(a);
			} else {
				table[curr_volume][index] = new Solution(b);
			}
			*/

			for(int i = 0; i < list.size(); i++) {
				int tmp = list.get(i).get(index);
				list.get(i).set(index, i);
				if(!list.get(i).isFeasible()) {
					list.get(i).set(index, tmp);
				} else {
					if(list.get(i).getValue() > table[curr_volume][index][packed].getValue()) {
						table[curr_volume][index][packed] = new Solution(list.get(i));
					}
				}
			}
			//returns current cell
			return table[curr_volume][index][packed];
		}
		return new Solution(instance);
	}
}