/**
 * 
 */
package rc.imp.ga;

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
		this.setChromosome(chromosome);
	}
	
	public Individual(int chromosomeLength) {
		this.chromosome = new int[chromosomeLength];
		for (int gene = 0 ; gene < chromosomeLength; gene++) {
			if (0.5 < Math.random()) {
				this.setGene(gene, 1);
			} else {
				this.setGene(gene, 0);
			}
		}
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

	/**
	 * @param chromosome the chromosome to set
	 */
	public void setChromosome(int[] chromosome) {
		this.chromosome = chromosome;
	}

	/**
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

}
