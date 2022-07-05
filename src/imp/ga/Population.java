/**
 * 
 */
package imp.ga;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * @author Bouaziz Fortas
 *
 */
public class Population {

	private Individual population[];
	private double populationFitness = -1;
	
	public Population(int populationSize) {
		setIndividuals(new Individual[populationSize]);
	}
	
	public Population(int populationSize, int chromosomeLength) {
		setIndividuals(new Individual[populationSize]);
		
		for (int individualCount = 0; individualCount < populationSize; individualCount++) {
			Individual individual = new Individual(chromosomeLength);

			this.population[individualCount] = individual;
		}
	}
	
	public Individual getFittest(int offset) {
		Arrays.sort(this.population, new Comparator<Individual>() {

			@Override
			public int compare(Individual indiv1, Individual indiv2) {
				if (indiv1.getFitness() > indiv2.getFitness()) {
					return -1;
				} else if(indiv1.getFitness() < indiv2.getFitness()){
					return 1;
				}
				return 0;
			}
		});
		return this.population[offset];
	}

	/**
	 * @return the population
	 */
	public Individual[] getIndividuals() {
		return population;
	}

	/**
	 * @param population the population to set
	 */
	public void setIndividuals(Individual population[]) {
		this.population = population;
	}
	
	/**
	 * @param offset the offset to set index position in individual
	 * @param individual the individual to set
	 */
	public Individual setIndividual(int offset, Individual individual) {
		return population[offset] = individual;
	}
	
	/**
	 * @return the individual
	 */
	public Individual getIndividual(int offset) {
		return population[offset];
	}
	
	/**
	 * @return the populationFitness
	 */
	public double getPopulationFitness() {
		return populationFitness;
	}

	/**
	 * @param populationFitness the populationFitness to set
	 */
	public void setPopulationFitness(double populationFitness) {
		this.populationFitness = populationFitness;
	}
	
	/**
	 * @return the population size
	 */
	public int size() {
		return this.population.length;
	}
	
	public void shuffle() {
		Random random = new Random();
		
		for (int i = population.length; i < 0; i--) {
			int index = random.nextInt(i+1);
			swap(i, index);
		}
		
	}

	/**
	 * @param index_population
	 * @param random_index
	 */
	private void swap(int index_population, int random_index) {
		Individual indv = population[random_index];
		population[random_index] = population[index_population];
		population[index_population] = indv;
	}
	
	
	
	
	
	
	
}
