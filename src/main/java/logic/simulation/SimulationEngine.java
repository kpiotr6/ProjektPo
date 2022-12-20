package logic.simulation;

import logic.*;

import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private AbstractWorldMap map;

    public SimulationEngine(AbstractWorldMap map, Vector2d[] positions, int[][] genoms, Starter config){
        int index = 0;
        for ( Vector2d position: positions) {
            Animal currAnimal = new PredistinatedAnimal(position, genoms[index], config.getStartAnimalEnergy(), map);
            map.initAnimal(currAnimal);
            index += 1;
        }
        this.map = map;
    }

    public void run(){
        while (this.map.getAnimalCount() > 0){
            //System.out.println(this.map.getAnimalCount());
            this.map.kill();
            this.map.move();
            //TimeUnit.SECONDS.sleep(2);
            //System.out.print("m");
        }
    }
}
