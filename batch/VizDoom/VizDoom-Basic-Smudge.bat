cd ..
cd ..
java -jar dist/MM-NEATv2.jar runNumber:%1 randomSeed:%1 base:vizdoombasic trials:10 maxGens:100 mu:50 io:true netio:true mating:true task:edu.utexas.cs.nn.tasks.vizdoom.VizDoomBasicShootTask cleanOldNetworks:true fs:false noisyTaskStat:edu.utexas.cs.nn.util.stats.Average log:Basic-Smudge saveTo:Smudge gameWad:freedoom2.wad watch:false stepByStep:false doomInputPixelSmudge:2