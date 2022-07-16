/**
 * 
 */
package imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class GA {

	private int populationSize;
	private double mutaionRate;
	private double crossoverRate;
	private int elitismCount;

	/**
	 * @param populationSize
	 * @param mutaionRate
	 * @param crossoverRate
	 * @param elitismCount
	 */
	public GA(int populationSize, double mutaionRate, double crossoverRate, int elitismCount) {
		this.populationSize = populationSize;
		this.mutaionRate = mutaionRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
	}

	/**
	 * @return the populationSize
	 */
	public int getPopulationSize() {
		return populationSize;
	}

	/**
	 * @param populationSize the populationSize to set
	 */
	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	/**
	 * @return the mutaionRate
	 */
	public double getMutaionRate() {
		return mutaionRate;
	}

	/**
	 * @param mutaionRate the mutaionRate to set
	 */
	public void setMutaionRate(double mutaionRate) {
		this.mutaionRate = mutaionRate;
	}

	/**
	 * @return the crossoverRate
	 */
	public double getCrossoverRate() {
		return crossoverRate;
	}

	/**
	 * @param crossoverRate the crossoverRate to set
	 */
	public void setCrossoverRate(double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	/**
	 * @return the elitismCount
	 */
	public int getElitismCount() {
		return elitismCount;
	}

	/**
	 * @param elitismCount the elitismCount to set
	 */
	public void setElitismCount(int elitismCount) {
		this.elitismCount = elitismCount;
	}

	/**
	 * @param chromosomeLength for create new population with some size
	 */
	public Population initPopulation(int chromosomeLength) {
		Population population = new Population(this.populationSize, chromosomeLength);
		return population;
	}

	/*
	 * This method should count the number of ones in the chromosome, and then
	 * normalize the output to be between zero and one by dividing by the chromosome
	 * length.
	 */
	public double calcFitness(Individual individual) {

		// Track number of correct genes
		int correctGenes = 0;

		// Loop over individual's genes
		for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
			// Add one fitness point for each "1" found
			if (individual.getGene(geneIndex) == 1) {
				correctGenes += 1;
			}
		}

		// Calculate fitness
		double fitness = (double) correctGenes / individual.getChromosomeLength();

		// Store fitness
		individual.setFitness(fitness);

		return fitness;
	}

	/**
	 * @param population
	 */
	public void evalPopulation(Population population) {
		double populationFitness = 0;

		for (Individual individual : population.getIndividuals()) {
			populationFitness += calcFitness(individual);
		}

		population.setPopulationFitness(populationFitness);
	}

	/*
	 * we must first construct a function which can check if our termination
	 * condition has occurred
	 */
	public boolean isTerminationConditionMet(Population population) {
		for (Individual individual : population.getIndividuals()) {
			if (individual.getFitness() == 1) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Choose a random number between 0 and the total population fitness, then loop
	 * through each individual, summing their fitnesses as you go, until you reach
	 * the random position you chose at the outset.
	 */
	public Individual selectParent(Population population) {
		// Get Individuals
		Individual individuals[] = population.getIndividuals();

		// spin roulette wheel
		double populationFitness = population.getPopulationFitness();
		double rouletteWheelPosition = Math.random() * populationFitness;

		// Find parent
		double spinWheel = 0;
		for (Individual individual : individuals) {
			spinWheel += individual.getFitness();
			if (spinWheel >= rouletteWheelPosition) {
				return individual;
			}
		}

		return individuals[population.size() - 1];
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

	/*
	 * method starts by creating a new empty population for the mutated individuals
	 * and then begins to loop over the current population. Each individual’s
	 * chromosome is then looped over and each gene is considered for bit flip
	 * mutation using the mutation rate. When the entire chromosome of an individual
	 * has been looped over, the individual is then added to the new mutation
	 * population. When all individuals have gone through the mutation process the
	 * mutated population is returned.
	 */
	public Population mutatePopulation(Population population) {
		// Initialize new population
		Population newPopulation = new Population(this.populationSize);

		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);

			// Loop over individual's genes
			for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
				// Skip mutation if tis is an elite individual
				if (populationIndex >= this.elitismCount) {
					// Does tis gene need mutation?
					if (this.mutaionRate > Math.random()) {
						// Get new gene
						int newGene = 1;
						if (individual.getGene(geneIndex) == 1) {
							newGene = 0;
						}
						// Mutate gene
						individual.setGene(geneIndex, newGene);
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
