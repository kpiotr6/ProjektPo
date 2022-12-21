package logic.simulation;

import logic.*;

import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private AbstractWorldMap map;

    public SimulationEngine(AbstractWorldMap map, Vector2d[] positions, int[][] genoms, Starter config){
        int index = 0;
        for ( Vector2d position: positions) {
            Animal currAnimal;
            if (config.getAnimalBehaviour() == AnimalBehaviour.FULL_PREDISTINATION){
                currAnimal = new PredistinatedAnimal(position, genoms[index], config.getStartAnimalEnergy(), map);
            }
            else{
                currAnimal = new MadAnimal(position, genoms[index], config.getStartAnimalEnergy(), map);
            }
            map.initAnimal(currAnimal);
            index += 1;
        }
        this.map = map;
    }

    public void run(){

        this.map.grow();

        while (this.map.getAnimalCount() > 0){
//            System.out.println(this.map.getAnimalCount());
            System.out.print(this.map);
            this.map.kill();
            this.map.move();

            this.map.consume();
            this.map.multiplicate();
            this.map.grow();
            try{
                TimeUnit.SECONDS.sleep(4);
            }catch (Exception e){
                System.out.println(e);
            }


        }
    }
}
