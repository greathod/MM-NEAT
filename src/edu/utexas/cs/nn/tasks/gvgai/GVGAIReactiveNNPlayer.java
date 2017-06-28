package edu.utexas.cs.nn.tasks.gvgai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import edu.utexas.cs.nn.networks.Network;
import edu.utexas.cs.nn.parameters.Parameters;
import edu.utexas.cs.nn.util.stats.StatisticsUtilities;
import gvgai.core.game.Observation;
import gvgai.core.game.StateObservation;
import gvgai.core.player.AbstractPlayer;
import gvgai.ontology.Types.ACTIONS;
import gvgai.tools.ElapsedCpuTimer;

public class GVGAIReactiveNNPlayer<T extends Network> extends AbstractPlayer {
	
	public static Network network;
	public static final double BIAS = 1.0;
	
	private int viewRange;
	
	public GVGAIReactiveNNPlayer(){
		viewRange = Parameters.parameters.integerParameter("gvgaiReactField");
	}
	
	public GVGAIReactiveNNPlayer(StateObservation stateObs, ElapsedCpuTimer elapsedTimer){
		viewRange = Parameters.parameters.integerParameter("gvgaiReactField");
	}
	
	@Override
	public ACTIONS act(StateObservation stateObs, ElapsedCpuTimer elapsedTimer) {
		List<Observation>[][] obsGrid = stateObs.getObservationGrid();
		
		int XPos = (int) stateObs.getAvatarPosition().x;
		int YPos = (int) stateObs.getAvatarPosition().y;
		
		int XMin = Math.max(0, XPos - viewRange); // Prevents the View Range from being Out of Bounds
		int XMax = Math.min(obsGrid.length, YPos + viewRange); // Prevents the View Range from being Out of Bounds
		
		int YMin = Math.max(0, YPos - viewRange); // Prevents the View Range from being Out of Bounds
		int YMax = Math.min(obsGrid.length, YPos + viewRange); // Prevents the View Range from being Out of Bounds
		
		int[][][] spriteIDs = new int[XMax - XMin][YMax - YMin][]; // Stores all Sprite IDs
		
		// Searches the area of the Observation Grid that the Player is allowed to see
		for(int x = XMin; x < XMax; x++){
			for(int y = YMin; y < YMax; y++){
				spriteIDs[x][y] = new int[obsGrid[x][y].size()];
				int index = 0;
				// Cycles through the Observations at a specific point on the Grid
				for(Observation obs : obsGrid[x][y]){
					spriteIDs[x][y][index++] = obs.itype; // ID of a specific Sprite on this point of the Grid
				}
			}
		}
		
		return react(spriteIDs);
	}

	public ACTIONS react(int[][][] spriteIDs){
		int playPos = (viewRange / 2); // The Player is in the center of the Grid
		
		// Stores the Network's evaluations
		double[][] spaceValues = new double[viewRange][viewRange];
		double bestValue = Double.NEGATIVE_INFINITY;
		Point bestSpace = null;
		
		for(int x = 0; x < viewRange; x++){
			for(int y = 0; y < viewRange; y++){
				// Converts the Sprite IDs into doubles for processing
				double[] spriteValues = new double[spriteIDs[x][y].length];
				for(int i = 0; i < spriteValues.length; i++){
					spriteValues[i] = (double) spriteIDs[x][y][i];
				}
				double value = network.process(spriteValues)[0]; // Stores this space's evaluated value
				spaceValues[x][y] = value;
				
				if(value > bestValue){
					bestValue = value;
					bestSpace = new Point(x, y);
				}
			}
		}
		
		List<ACTIONS> moves = new ArrayList<ACTIONS>(); // Stores the Move considered most favorable
		
		// Selects the Move based on the evaluation
		if(bestSpace == new Point(playPos, playPos)){ // Player is in the best evaluated space right now
			moves.add(ACTIONS.ACTION_NIL);
		}else{
			// Add best possible Moves to a List
			if(bestSpace.y < playPos){ // Best evaluated space is above the Player
				moves.add(ACTIONS.ACTION_UP);
			}else if(bestSpace.y > playPos){ // Best evaluated space is below the Player
				moves.add(ACTIONS.ACTION_DOWN);
			}

			if(bestSpace.x < playPos){ // Best evaluated space is left of the Player
				moves.add(ACTIONS.ACTION_LEFT);
			}else if(bestSpace.x > playPos){ // Best evaluated space is right of the Player
				moves.add(ACTIONS.ACTION_RIGHT);
			}
		}
		
		// If only one move is considered favorable, take it; TODO: Better OneStepEval here?
		if(moves.size() == 1){
			return moves.get(0);
		}else{
			double[] nextSpaceEval = new double[moves.size()];
			int index = 0;
			// Must be Up, Down, Left, or Right; cannot be Nil
			for(ACTIONS act : moves){
				if(act == ACTIONS.ACTION_UP){
					nextSpaceEval[index++] = spaceValues[playPos][playPos-1];
				}else if(act == ACTIONS.ACTION_DOWN){
					nextSpaceEval[index++] = spaceValues[playPos][playPos+1];
				}else if(act == ACTIONS.ACTION_LEFT){
					nextSpaceEval[index++] = spaceValues[playPos-1][playPos];
				}else if(act == ACTIONS.ACTION_RIGHT){
					nextSpaceEval[index++] = spaceValues[playPos+1][playPos];
				}
			}
			
			return moves.get(StatisticsUtilities.argmax(nextSpaceEval));
		}
	}
	
}
