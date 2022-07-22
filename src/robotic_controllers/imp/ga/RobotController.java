package robotic_controllers.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class RobotController {
	public static int maxGenerations = 500;

	public static void main(String[] args) {
		
		/**
		* 0 = Empty
		* 1 = Wall
		* 2 = Starting position
		* 3 = Route
		* 4 = Goal position
		*/
		
		Maze maze = new Maze(new int[][] {
			{ 0, 0, 0, 0, 1, 0, 1, 3, 2 },
			{ 1, 0, 1, 1, 1, 0, 1, 3, 1 },
			{ 1, 0, 0, 1, 3, 3, 3, 3, 1 },
			{ 3, 3, 3, 1, 3, 1, 1, 0, 1 },
			{ 3, 1, 3, 3, 3, 1, 1, 0, 0 },
			{ 3, 3, 1, 1, 1, 1, 0, 1, 1 },
			{ 1, 3, 0, 1, 3, 3, 3, 3, 3 },
			{ 0, 3, 1, 1, 3, 1, 0, 1, 3 },
			{ 1, 3, 3, 3, 3, 1, 1, 1, 4 }
		});
		
		// Create genetic algorithm
		GA ga = new GA(200, 0.05, 0.9, 2, 10);
		Population population = ga.initPopulation(128);
		
		// Evaluate population
		ga.evalPopulation(population, maze);

		int generation = 1;
		
		// Start evolution loop
		while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {
			// Print fittest individual from population
			Individual fittest = population.getFittest(0);
			System.out.println(/*"G" + generation + " Best solution (" + fittest.getFitness() + "): " +*/ fittest.toString());
			
			// Apply crossover
			population = ga.crossoverPopulation(population);
			
			// Apply mutation
			population = ga.mutatePopulation(population);
			
			// Evaluate population
			ga.evalPopulation(population, maze);
			
			// Increment the current generation
			generation++;
		}
		
		System.out.println("Stopped after " + maxGenerations + " generations.");
				Individual fittest = population.getFittest(0);
				System.out.println(/*"Best solution (" + fittest.getFitness() + "): " +*/ fittest.toString());
	}

}
