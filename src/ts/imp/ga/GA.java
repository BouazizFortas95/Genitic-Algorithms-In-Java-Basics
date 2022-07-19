package ts.imp.ga;

import java.util.Arrays;

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

	/**
	 * @param chromosomeLength for create new population with some size
	 */
	public Population initPopulation(int chromosomeLength) {
		Population population = new Population(this.populationSize, chromosomeLength);
		return population;
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

	/*
	 * the fitness is calculated by dividing 1 by the total route distance a shorter
	 * distance therefore has a higher score. After the fitness has been calculated,
	 * it is stored for quick recall in case it is needed again.
	 */
	public double calcFitness(Individual individual, City cities[]) {
		// Get fitness
		Route route = new Route(individual, cities);
		double fitness = 1 / route.getDistance();
		// Store fitness
		individual.setFitness(fitness);
		return fitness;
	}

	/*
	 * this function loops over the population and each individual’s fitness is
	 * calculated. Unlike previous implementations, we’re calculating an average
	 * population fitness instead of a total population fitness
	 */
	public void evalPopulation(Population population, City cities[]) {
		double populationFitness = 0;
		// Loop over population evaluating individuals and summing population fitness
		for (Individual individual : population.getIndividuals()) {
			populationFitness += this.calcFitness(individual, cities);
		}
		double avgFitness = populationFitness / population.size();
		population.setPopulationFitness(avgFitness);
	}

	/*
	 * We first create a new population to hold the offspring. Then, the current
	 * population is looped over in the order of the fittest individual first. If
	 * elitism is enabled, the first few elite individuals are skipped over and
	 * added to the new population unaltered. The remaining individuals are then
	 * considered for crossover using the crossover rate. If crossover is to be
	 * applied to the individual, a parent is selected using the selectParent method
	 * and a new blank individual is created. Next, two random positions in the
	 * parent1’s chromosome are picked and the subset of genetic information between
	 * those positions are added to the offspring’s chromosome. Finally, the
	 * remaining genetic information needed is added in the order found in parent2;
	 * then when complete, the individual is added into the new population
	 */
	public Population crossoverPopulation(Population population) {
		// Create new population
		Population newPopulation = new Population(population.size());
		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			// Get parent1
			Individual parent1 = population.getFittest(populationIndex);
			// Apply crossover to this individual?
			if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
				// Find parent2 with tournament selection
				Individual parent2 = this.selectParent(population);
				// Create blank offspring chromosome
				int offspringChromosome[] = new int[parent1.getChromosomeLength()];
				Arrays.fill(offspringChromosome, -1);
				Individual offspring = new Individual(offspringChromosome);
				// Get subset of parent chromosomes
				int substrPos1 = (int) (Math.random() * parent1.getChromosomeLength());
				int substrPos2 = (int) (Math.random() * parent1.getChromosomeLength());
				// make the smaller the start and the larger the end
				final int startSubstr = Math.min(substrPos1, substrPos2);
				final int endSubstr = Math.max(substrPos1, substrPos2);
				// Loop and add the sub tour from parent1 to our child
				for (int i = startSubstr; i < endSubstr; i++) {
					offspring.setGene(i, parent1.getGene(i));
				}
				// Loop through parent2's city tour
				for (int i = 0; i < parent2.getChromosomeLength(); i++) {
					int parent2Gene = i + endSubstr;
					if (parent2Gene >= parent2.getChromosomeLength()) {
						parent2Gene -= parent2.getChromosomeLength();
					}
					// If offspring doesn't have the city add it
					if (offspring.containsGene(parent2.getGene(parent2Gene)) == false) {
						// Loop to find a spare position in the child's tour
						for (int j = 0; j < offspring.getChromosomeLength(); j++) {
							// Spare position found, add city
							if (offspring.getGene(j) == -1) {
								offspring.setGene(j, parent2.getGene(parent2Gene));
								break;
							}
						}
					}
				}
				// Add child
				newPopulation.setIndividual(populationIndex, offspring);
			} else {
				// Add individual to new population without applying crossover
				newPopulation.setIndividual(populationIndex, parent1);
			}
		}
		return newPopulation;
	}

	/*
	 * The first step of this method is to create a new population to hold the
	 * mutated individuals. Next, the population is looped over starting with the
	 * fittest individuals. If elitism is enabled, the first few individuals are
	 * skipped and added to the new population unaltered. The chromosomes from the
	 * remaining individuals are then looped over and each gene is considered for
	 * mutation individually depending on the mutation rate. If a gene is to be
	 * mutated, another random gene from the individual is picked and the genes are
	 * swapped. Finally, the mutated individual is added to the new population.
	 */
	public Population mutatePopulation(Population population) {
		// Initialize new population
		Population newPopulation = new Population(this.populationSize);
		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);
			// Skip mutation if this is an elite individual
			if (populationIndex >= this.elitismCount) {
				// System.out.println("Mutating population member "+populationIndex);
				// Loop over individual's genes
				for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
					// Does this gene need mutation?
					if (this.mutationRate > Math.random()) {
						// Get new gene position
						int newGenePos = (int) (Math.random() * individual.getChromosomeLength());
						// Get genes to swap
						int gene1 = individual.getGene(newGenePos);
						int gene2 = individual.getGene(geneIndex);
						// Swap genes
						individual.setGene(geneIndex, gene1);
						individual.setGene(newGenePos, gene2);
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
