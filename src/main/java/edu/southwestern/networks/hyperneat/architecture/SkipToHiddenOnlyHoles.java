package edu.southwestern.networks.hyperneat.architecture;

import java.util.ArrayList;
import java.util.List;

import edu.southwestern.networks.hyperneat.HyperNEATTask;
import edu.southwestern.networks.hyperneat.SubstrateConnectivity;
import edu.southwestern.util.datastructures.Pair;
import edu.southwestern.util.datastructures.Triple;

/**
 * Inputs are connected to the first hidden layer layer by a 3x3 receptive field. The first hidden layer is connected to 
 * the second hidden layer by a 1x1 receptive field. The second input (holes sensor in Tetris with split sensors) is connected
 * to the second hidden layer with a 3x3 receptive field
 * @author Devon Fulcher
 */
public class SkipToHiddenOnlyHoles implements SubstrateArchitectureDefinition{

	@Override
	public List<Triple<Integer, Integer, Integer>> getNetworkHiddenArchitecture() {
		List<Triple<Integer, Integer, Integer>> networkHiddenArchitecture = new ArrayList<Triple<Integer, Integer, Integer>>();
		networkHiddenArchitecture.add(new Triple<Integer, Integer, Integer>(1, 8, 18));
		networkHiddenArchitecture.add(new Triple<Integer, Integer, Integer>(1, 8, 18));
		return networkHiddenArchitecture;
	}

	@Override
	public List<SubstrateConnectivity> getSubstrateConnectivity(HyperNEATTask hnt) {
		List<SubstrateConnectivity> substrateConnectivity = new ArrayList<SubstrateConnectivity>();
		Pair<List<String>, List<String>> io = FlexibleSubstrateArchitecture.getInputAndOutputNames(hnt);
		List<Triple<Integer, Integer, Integer>> networkHiddenArchitecture = getNetworkHiddenArchitecture();
		FlexibleSubstrateArchitecture.connectInputToFirstHidden(substrateConnectivity, io.t1, networkHiddenArchitecture, 3, 3);
		List<String> reducedIo = new ArrayList<String>();
		reducedIo.add(io.t1.get(1));
		FlexibleSubstrateArchitecture.connectInputToHidden(substrateConnectivity, reducedIo, networkHiddenArchitecture, 3, 3, 1);
		FlexibleSubstrateArchitecture.connectAllAdjacentHiddenLayers(substrateConnectivity, networkHiddenArchitecture, 1, 1);
		FlexibleSubstrateArchitecture.connectLastHiddenToOutput(substrateConnectivity, io.t2, networkHiddenArchitecture, SubstrateConnectivity.CTYPE_FULL);
		return substrateConnectivity;
	}

}
