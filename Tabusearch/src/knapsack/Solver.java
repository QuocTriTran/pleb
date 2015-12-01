package knapsack;

import java.util.*;

public class Solver implements SolverInterface {
	private int method = 0;
	
	public Solver() {}
	
	public Solver(int method) {
		this.method = method;
	}
	
	/**
	 * Solves 0-1 knapsack with different attributes and stop criteria
	 * 
	 * cases -
	 * 0: Long term memory, flip, TL = position of flip, include only feasible
	 */
	public Solution solve(Instance instance) {
		Solution start = new Solution(instance);
		Solution curr = new Solution(start);
		Solution best = new Solution(curr);
		
		switch (method) {
		case 0:
			best = new Solution(solve0(start));
			break;
		case 1:
			break;
		case 2:
			break;

		default:
			break;
		}
		
		return best;
	}
	
	private Solution solve0(Solution start) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		return start;
	}
	
	private ArrayList<Solution> neighbour(Solution sol, int method) {
		ArrayList<Solution> neighb = new ArrayList<Solution>();
		for(int i = 0; i < sol.getSize(); i++) {
			flip(i, sol);
		}
		return neighb;
	}
	
	/**
	 * Tabulist that contains 2 elements that have been swapped already.
	 * @param list
	 * @param e
	 * @param e2
	 */
	private void addToList(ArrayList list, Item e, Item e2) {
		Item[] items = new Item[2];
		items[0] = new Item(e);
		items[1] = new Item(e2);
		list.add(items);
	}
	
	
	/**
	 * 
	 * @param items
	 * @param start
	 * @param des
	 */
	private void swapItems(Item[] items, int start, int des) {
		Item tmp = new Item(items[start]);
		items[start] = new Item(items[des]);
		items[des] = new Item(tmp);
	}
	
	/**
	 * flips a Solution bit at given position
	 * @param pos
	 * @param solution
	 */
	private void flip(int pos, Solution solution) {
		if (pos > solution.getSize() - 1) {
			return;
		}

		if (solution.get(pos) == 1)
			solution.set(pos, 0);
		else
			solution.set(pos, 1);
	}
}