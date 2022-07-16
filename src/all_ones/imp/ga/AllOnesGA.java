/**
 * 
 */
package all_ones.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class AllOnesGA {

	public static void main(String[] args) {

		// create a GA object
		GA ga = new GA(100, 0.001, 0.95, 2);
		
		// Initialize population
		Population population = ga.initPopulation(50);
		
		// Evaluate population
		ga.evalPopulation(population);
		
		// Keep track of current generation
		int generation = 1;
		
		while (ga.isTerminationConditionMet(population) == false) {
			// Print fittest individual from population
			System.out.println("Best Solution : "+ population.getFittest(0).toString());
			
			// Apply crossover
			population = ga.crossoverPopulation(population);
			
			// Apply mutation
			population = ga.mutatePopulation(population);
			
			// Evaluate population
			ga.evalPopulation(population);
			
			// Increment the correct generation
			generation++;
		}
		
		System.out.println("Found Solution in : "+ generation +" generations");
		System.out.println("Best Solution: "+ population.getFittest(0).toString());
		
	}

}
