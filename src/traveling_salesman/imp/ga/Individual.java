package traveling_salesman.imp.ga;

/**
 * @author Bouaziz Fortas
 *
 */
public class Individual {

	private int[] chromosome;
	private double fitness = -1;

	/**
	 * @param chromosome
	 */
	public Individual(int[] chromosome) {
		// Create individual chromosome
		this.chromosome = chromosome;
	}

	public Individual(int chromosomeLength) {
		// Create random individual
		int[] individual;
		individual = new int[chromosomeLength];
		for (int gene = 0; gene < chromosomeLength; gene++) {
			individual[gene] = gene;
		}
		this.chromosome = individual;
	}
	
	/**
	 * @param offset the offset to select index in chromosome
	 * @param gene the gene to set change value selected by offset
	 */
	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}
	
	/**
	 * @return the chromosome
	 */
	public int getGene(int offset) {
		return this.chromosome[offset];
	}

	
	/**
	 * @return the chromosome
	 */
	public int[] getChromosome() {
		return chromosome;
	}

	/**
	 * @return the chromosome length
	 */
	public int getChromosomeLength() {
		return this.chromosome.length;
	}
	/*
	 * @return the fitness
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * @param fitness the fitness to set
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public String toString() {

		String output = "";
		for (int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene];
		}
		return output;
	}

	/*
	 * This method looks at each gene in the chromosome, and if it finds the gene
	 * it’s looking for it will return true; otherwise it returns false.
	 */
	public boolean containsGene(int gene) {
		for (int i = 0; i < this.chromosome.length; i++) {
			if (this.chromosome[i] == gene) {
				return true;
			}
		}
		return false;
	}

}
