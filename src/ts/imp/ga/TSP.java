package ts.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class TSP {

	public static int maxGenerations = 3000;

	public static void main(String[] args) {

		int numCities = 100;
		City cities[] = new City[numCities];
		// Loop to create random cities
		for (int cityIndex = 0; cityIndex < numCities; cityIndex++) {
			int xPos = (int) (100 * Math.random());
			int yPos = (int) (100 * Math.random());
			// Add city
			cities[cityIndex] = new City(xPos, yPos);
		}
		// Initial GA
		GA ga = new GA(100, 0.001, 0.9, 2, 5);

		// Initialize population
		Population population = ga.initPopulation(cities.length);

		// Evaluate population
		ga.evalPopulation(population, cities);

		// Keep track of current generation
		int generation = 1;

		// Start evolution loop
		while (ga.isTerminationConditionMet(generation, maxGenerations) == false) {

			// Print fittest individual from population
			Route route = new Route(population.getFittest(0), cities);
			System.out.println("G" + generation + " Best distance: " + route.getDistance());

			// Apply crossover
			population = ga.crossoverPopulation(population);

			// Apply mutation
			population = ga.mutatePopulation(population);

			// Evaluate population
			ga.evalPopulation(population, cities);

			// Increment the current generation
			generation++;
		}
		// Display results
		System.out.println("Stopped after " + maxGenerations + " generations.");
		Route route = new Route(population.getFittest(0), cities);
		System.out.println("Best distance: " + route.getDistance());
	}
}
