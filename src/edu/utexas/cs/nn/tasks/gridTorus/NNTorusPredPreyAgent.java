package edu.utexas.cs.nn.tasks.gridTorus;

/**
 * Imports needed parts to initialize the PredPreyAgent, as in organism, genotypes, and networks
 */
import edu.utexas.cs.nn.evolution.Organism;
import edu.utexas.cs.nn.evolution.genotypes.Genotype;
import edu.utexas.cs.nn.networks.Network;
import edu.utexas.cs.nn.parameters.Parameters;

/**
 * 
 * @author Jacob Schrum, Gabby Gonzalez, Alex Rollins The following class sets
 *         up the PredPreyAgent to utilize Organism and Network and initializes
 *         the brain and controller.
 */
public class NNTorusPredPreyAgent<T extends Network> extends Organism<T> {

	/**
	 * Initialize controller
	 */
	public NNTorusPredPreyController controller;

	/**
	 * Uses the genotype to finish the set up of the controller. It accesses the
	 * network to do this.
	 * 
	 * @param genotype
	 *            encodes agent phenotype controller
	 * @param isPredator
	 *            true if this agent is a predator, false if it is a prey
	 */
	public NNTorusPredPreyAgent(Genotype<T> genotype, boolean isPredator) {
		super(genotype);
		Network net = (Network) this.getGenotype().getPhenotype();
		controller = Parameters.parameters.booleanParameter("hyperNEAT")
				? new HyperNEATNNTorusPredPreyController(net, isPredator)
				: new NNTorusPredPreyController(net, isPredator);
	}

	/**
	 * Getter function to access the controller of the PredPreyAgent
	 * 
	 * @return controller
	 */
	public NNTorusPredPreyController getController() {
		return controller;
	}
}
