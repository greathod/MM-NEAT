package edu.utexas.cs.nn.tasks.microrts.iterativeevolution;

import micro.ai.RandomBiasedAI;
import micro.ai.abstraction.partialobservability.POLightRush;
import micro.ai.abstraction.partialobservability.POWorkerRush;
import micro.ai.core.AI;
import micro.ai.mcts.naivemcts.NaiveMCTS;

/**
 * sequence of the static opponents that are used in the
 * MicroRTS competition
 * 
 * @author alicequint
 *
 */
public class CompetitiveEnemySequence implements EnemySequence{

	private static final int gensPerEnemy = 20;
	
	private final AI[] enemies = new AI[]{
			new RandomBiasedAI(), //used in competition
			new POWorkerRush(), //used in competition
			new POLightRush(),  //used in competition
			new NaiveMCTS(), //used in competition
		};
	
	@Override
	public AI getAppropriateEnemy(int generation) {
		return enemies[Math.min(generation/gensPerEnemy, enemies.length-1)]; 
	}

}