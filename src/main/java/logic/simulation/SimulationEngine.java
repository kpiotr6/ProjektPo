package logic.simulation;

import logic.*;

import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private AbstractWorldMap map;

    public SimulationEngine(AbstractWorldMap map, Vector2d[] positions, int[][] genoms, Starter config){
        int index = 0;
        for ( Vector2d position: positions) {
            Animal currAnimal = new MadAnimal(position, genoms[index], config.getStartAnimalEnergy(), map);
            map.initAnimal(currAnimal);
            index += 1;
        }
        this.map = map;
    }

    public void run(){
//        int i =0;
        while (this.map.getAnimalCount() > 0){
//            System.out.println(this.map.getAnimalCount());
            System.out.print(this.map);
            this.map.grow();
            this.map.kill();
            this.map.move();
//            if(i>3){
//                return;
//            }
//            i+=1;
            //TimeUnit.SECONDS.sleep(2);

        }
    }
}
