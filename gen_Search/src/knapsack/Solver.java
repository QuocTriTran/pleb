package knapsack;

import java.util.*;

public class Solver implements SolverInterface {
	private final int generations = 1;
	
	private int populationSize;
	private Instance instance;
	private Solution filled;
	
	
	public Solution solve(Instance instance) {
		populationSize = 10;
		Solution[] population = new Solution[populationSize];
		this.instance = instance;
		filled = new Solution(instance);
		for(int i = 0; i < instance.getSize(); i++) {
			filled.set(i, 1);
		}
		
		for(int i = 0; i < populationSize; i++) {
			population[i] = new Solution(generateSolution());
		}
		sortPopulationDesc(population);
		
		Solution one = onePoint(population);
		Solution two = twoPoint(population);
		
		return one;
	}
	
	private Solution onePoint(Solution[] p) {
		Solution[] population = Arrays.copyOf(p, instance.getSize());
		
		for(int i = 0; i < generations; i++) {
			Random rand = new Random(1337);
			Solution m = new Solution(population[1]);
			Solution f = new Solution(population[0]);
			Solution c = new Solution(filled);
			
			while(!c.isFeasible()) {
				c = new Solution(onePointCrossover(m, f, rand.nextInt(instance.getSize())));
			}
			c = new Solution(mutate(c));
			population[instance.getSize() - 1] = new Solution(c);
			sortPopulationDesc(population);
		}
		return population[0];
	}
	
	private Solution twoPoint(Solution[] p) {
		Solution[] population = Arrays.copyOf(p, instance.getSize());
		
		for(int i = 0; i < generations; i++) {
			Random rand = new Random(1337);
			Solution m = new Solution(population[1]);
			Solution f = new Solution(population[0]);
			Solution c = new Solution(filled);
			
			while(!c.isFeasible()) {
				int pos2 = Math.abs(rand.nextInt() % instance.getSize());
				int pos1 = Math.abs(rand.nextInt() % pos2);
				c = new Solution(twoPointCrossover(m, f, pos1, pos2));
			}
			c = new Solution(mutate(c));
			population[instance.getSize() - 1] = new Solution(c);
			sortPopulationDesc(population);
		}
		
		return population[0];
	}
	
	/**
	 * Sorts population in ascending order
	 * @param population
	 */
	private void sortPopulationAsc(Solution[] population) {
		Arrays.sort(population, new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return Integer.compare(o1.getValue(), o2.getValue());
			}
		});
	}
	
	/**
	 * Sorts population in descending order
	 * @param population
	 */
	private void sortPopulationDesc(Solution[] population) {
		Arrays.sort(population, new Comparator<Solution>() {
			@Override
			public int compare(Solution o1, Solution o2) {
				return Integer.compare(o2.getValue(), o1.getValue());
			}
		});
	}
	
	/**
	 * 
	 * @param m mother Solution
	 * @param f father Solution
	 * @param pos position to use crossover on
	 * @return child of m and f
	 */
	private Solution onePointCrossover(Solution f, Solution m, int pos) {
		Solution sol = new Solution(instance);
		
		for(int i = 0; i < pos; i++) {
			sol.set(i, f.get(i));
		}
		for(int i = pos; i < instance.getSize(); i++) {
			sol.set(i, m.get(i));
		}
		
		return sol;
	}
	
	private Solution twoPointCrossover(Solution m, Solution f, int pos1, int pos2) {
		Solution sol = new Solution(instance);
		
		for(int i = 0; i < pos1; i++) {
			sol.set(i, m.get(i));
		}
		for(int i = pos1; i < pos2; i++) {
			sol.set(i, f.get(i));
		}
		for(int i = pos2; i < instance.getSize(); i++) {
			sol.set(i, m.get(i));
		}
		return sol;
	}
	
	private Solution generateSolution() {
		Solution sol = new Solution(instance);
		Random rand = new Random(System.currentTimeMillis());
		Random rand1 = new Random(1337); //fixed seed for testing purposes

		for(int i = 0; i < Math.abs(rand.nextInt()); i++) {
			flip(Math.abs(rand.nextInt() % instance.getSize()), sol);
		}
		
		while(!sol.isFeasible()) {
			flip(Math.abs(rand.nextInt() % instance.getSize()), sol);
		}
		
		return sol;
	}
	
    /**
     * Flips a single byte in a @Solution object
     *
     * @param pos      Determines the position of the byte that will be flipped
     * @param solution object that will be manipulated
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
    
    private Solution mutate(Solution solution) {
    	Solution sol = new Solution(solution);
    	Random rand = new Random(System.currentTimeMillis());
    	boolean check = false;
    	for(int i = 0; i < instance.getSize() || !check; i++) {
    		sol = new Solution(solution);
    		flip(i, sol);
    		if(sol.isFeasible()) {
    			check = true;
    		}
    	}
    	return sol;
    }
}