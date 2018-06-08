cd ..
cd ..
java -jar target/MM-NEAT-0.0.1-SNAPSHOT.jar runNumber:%1 randomSeed:%1 base:vizdoomdefendcenter trials:5 maxGens:500 mu:100 io:true netio:true mating:true task:edu.southwestern.tasks.vizdoom.VizDoomDefendCenterTask cleanOldNetworks:true fs:false noisyTaskStat:edu.southwestern.util.stats.Average log:DefendCenter-moHNRowConvolution saveTo:moHNRowConvolution gameWad:freedoom2.wad watch:false stepByStep:false doomEpisodeLength:2100 doomInputStartX:0 doomInputStartY:70 doomInputHeight:15 doomInputWidth:200 receptiveFieldWidth:5 receptiveFieldHeight:5 stride:2 moVizDoom:true hyperNEAT:true genotype:edu.southwestern.evolution.genotypes.HyperNEATCPPNGenotype allowMultipleFunctions:true ftype:1 netChangeActivationRate:0.3 printFitness:true convolution:true