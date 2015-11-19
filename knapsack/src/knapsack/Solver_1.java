package knapsack;

import java.util.*;

public class Solver_1 implements SolverInterface {

	private int weight_limit = 0;
	private int amount = 0;
	private int[][][] table; //saves instances of solution for easier recursion without the need
	//of using backtracking to trace the objects that will be taken
	Instance instance; // used to properly reduce weight in recursion
	
	public Solver_1() {}
	
	/**
	 * Solves the binary Knapsack by using dynamic programming
	 */
	@Override
	public Solution solve(Instance instance) {
		weight_limit = instance.getWeightLimit();
		amount = instance.getSize();
		this.instance = instance;
		
		table = new int[weight_limit + 1][amount][weight_limit + 1];
		
		for(int i = 0; i < weight_limit + 1; i++) {
			for(int j = 0; j < amount; j++) {
				for(int k = 0; k < weight_limit + 1; k++) {
					table[i][j][k] = -1;
				}
			}
		}
		Solution start = new Solution(instance);
		int sol = recursion(weight_limit, 0, start, 0);
		System.out.println(sol);
		
		for(int i = 0; i < weight_limit + 1; i++) {
			System.out.println("RESTWEIGHT : " + i + ": ");
			for(int j = 0; j < amount; j++) {
				for(int k = 0; k < weight_limit + 1; k++) {
					System.out.printf("%3d",table[i][j][k]);
				}
				System.out.println("");
			}
			System.out.println("");
		}

		return start;
	}
	
	/**
	 * Recursive part of dynamic programming
	 * @param curr_volume Usable volume
	 * @param index position of item
	 * @param sol previous solution to use the recursion on
	 * @return solution with highest amount of value that is still feasible
	 */
	private int recursion(int curr_volume, int index, Solution sol, int packed) {
		if(index < amount) { // checks if every item has been taken into consideration
			if(table[curr_volume][index][packed] != -1) { //checks if cell is empty
				return table[curr_volume][index][packed]; // if not return calculated solution
			}
			
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			for(int i = 0; i <= curr_volume; i++) {
				if(i == 0) {
					int a = recursion(curr_volume, index + 1, new Solution(sol), 0);
					list.add((Integer)a);
				} else {
					int a = 0;
					if(curr_volume - instance.getWeight(index) * i >= 0) {
						a = instance.getValue(index) * i + recursion(curr_volume - (instance.getWeight(index) * i),
								index + 1, new Solution(sol), i);
					}
					list.add((Integer)a);
				}
			}
			
			
			for(Integer i : list) {
				if(i.intValue() > table[curr_volume][index][packed]) {
					table[curr_volume][index][packed] = i.intValue();
				}
			}
			
			
			return table[curr_volume][index][packed];
		}
		return 0;
	}
}