cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:%1 randomSeed:%1 base:othello trials:10 maxGens:500 mu:100 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.boardGame.SinglePopulationCompetativeCoevolutionBoardGameTask cleanOldNetworks:true fs:false log:Othello-SinglePopulationCompetativeCoevolution saveTo:SinglePopulationCompetativeCoevolution boardGame:boardGame.othello.Othello