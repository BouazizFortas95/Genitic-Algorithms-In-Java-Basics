package class_scheduling.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class GA {

	private int populationSize;
	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;
	protected int tournamentSize;

	/**
	 * @param populationSize
	 * @param mutaionRate
	 * @param crossoverRate
	 * @param elitismCount
	 */
	public GA(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int tournamentSize) {
		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
		this.tournamentSize = tournamentSize;
	}

	/**
	 * @return the populationSize
	 */
	public int getPopulationSize() {
		return populationSize;
	}

	/**
	 * @return the mutaionRate
	 */
	public double getMutationRate() {
		return mutationRate;
	}

	/**
	 * @return the crossoverRate
	 */
	public double getCrossoverRate() {
		return crossoverRate;
	}

	/**
	 * @return the elitismCount
	 */
	public int getElitismCount() {
		return elitismCount;
	}

	public Population initPopulation(Timetable timetable) {
		// Initialize population
		Population population = new Population(this.populationSize, timetable);
		return population;
	}

	public boolean isTerminationConditionMet(Population population) {
		return population.getFittest(0).getFitness() == 1.0;
	}

	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations);
	}

	public Individual selectParent(Population population) {
		// Create tournament
		Population tournament = new Population(this.tournamentSize);
		// Add random individuals to the tournament
		population.shuffle();
		for (int i = 0; i < this.tournamentSize; i++) {
			Individual tournamentIndividual = population.getIndividual(i);
			tournament.setIndividual(i, tournamentIndividual);
		}
		// Return the best
		return tournament.getFittest(0);
	}

	public double calcFitness(Individual individual, Timetable timetable) {
		// Create new timetable object to use -- cloned from an existing timetable

		Timetable threadTimetable = new Timetable(timetable);
		threadTimetable.createClasses(individual);

		// Calculate fitness
		int clashes = threadTimetable.calcClashes();
		double fitness = 1 / (double) (clashes + 1);
		individual.setFitness(fitness);

		return fitness;
	}

	public void evalPopulation(Population population, Timetable timetable) {
		double populationFitness = 0;
		// Loop over population evaluating individuals and summing population fitness
		for (Individual individual : population.getIndividuals()) {
			populationFitness += this.calcFitness(individual, timetable);
		}
		population.setPopulationFitness(populationFitness);
	}

	/*
	 * In the first line of the crossoverPopulation() method, a new empty population
	 * is created for the next generation. Next, the population is looped over and
	 * the crossover rate is used to consider each individual for crossover. If the
	 * individual doesn’t go through crossover, its added straight to the next
	 * population, otherwise a new individual is created. The offspring’s chromosome
	 * is filled by looping over the parent chromosomes and randomly adding genes
	 * from each parent to the offspring’s chromosome. When this crossover process
	 * has finished for eachindividual of the population, the crossover method
	 * returns the next generation’s population.
	 */
	public Population crossoverPopulation(Population population) {

		// Create new population
		Population newPopulation = new Population(population.size());

		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual parent_1 = population.getFittest(populationIndex);

			// Apply crossover to this individual?
			if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
				// Initialize offspring
				Individual offspring = new Individual(parent_1.getChromosomeLength());

				// Find second parent
				Individual parent_2 = selectParent(population);

				// Loop over genome
				for (int geneIndex = 0; geneIndex < parent_1.getChromosomeLength(); geneIndex++) {
					// Use half of parent_1's genes and half of parent_2's genes
					if (0.5 > Math.random()) {
						offspring.setGene(geneIndex, parent_1.getGene(geneIndex));
					} else {
						offspring.setGene(geneIndex, parent_2.getGene(geneIndex));
					}
				}

				// Add offspring to new population
				newPopulation.setIndividual(populationIndex, offspring);
			} else {
				// Add individual to new population without applying crossover
				newPopulation.setIndividual(populationIndex, parent_1);
			}
		}
		return newPopulation;
	}

	public Population mutatePopulation(Population population, Timetable timetable) {
		// Initialize new population
		Population newPopulation = new Population(this.populationSize);
		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);
			// Create random individual to swap genes with
			Individual randomIndividual = new Individual(timetable);
			// Loop over individual's genes
			for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
				// Skip mutation if this is an elite individual
				if (populationIndex > this.elitismCount) {
					// Does this gene need mutation?
					if (this.mutationRate > Math.random()) {
						// Swap for new gene
						individual.setGene(geneIndex, randomIndividual.getGene(geneIndex));
					}
				}
			}
			// Add individual to population
			newPopulation.setIndividual(populationIndex, individual);
		}
		// Return mutated population
		return newPopulation;
	}
}
