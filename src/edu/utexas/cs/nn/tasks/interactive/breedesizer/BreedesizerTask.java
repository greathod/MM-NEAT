package edu.utexas.cs.nn.tasks.interactive.breedesizer;

import java.awt.image.BufferedImage;

import edu.utexas.cs.nn.networks.Network;
import edu.utexas.cs.nn.tasks.interactive.InteractiveEvolutionTask;

public class BreedesizerTask extends InteractiveEvolutionTask {

	public BreedesizerTask() throws IllegalAccessException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/* Will need method that plays sound when image is clicked. Can call utility methods
	 in edu.utexas.cs.nn.util.sounds to accomplish this, but will have to be differentiated 
	 from Picbreeder because it does not do this.
	 */
	
	/* After save and setEffectCheckbox are generalized so that they can be applied to both 
	 * Breedesizer and Picbreeder, specified method calls will have to be included here.
	 */

	@Override
	public String[] sensorLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] outputLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getWindowTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected BufferedImage getButtonImage(Network phenotype, int width, int height, double[] inputMultipliers) {
		// TODO Auto-generated method stub
		return null;
	}

}
