import logic.*;
import logic.enums.*;
import logic.maps.GlobeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiplicationTest {

    SimulationTestEngine engine;

    public Animal[] runEngine(Starter starter, AbstractWorldMap map, int numOfAnimals, Vector2d[] positions, int[][] genoms, MapDirection[] directions  ){
        Animal[] animals = new Animal[numOfAnimals];

        int index = 0;
        for ( Vector2d position: positions) {
            Animal currAnimal = new PredistinatedAnimal(position, genoms[index], starter.getStartAnimalEnergy(), map);
            currAnimal.setMapDirection(directions[index]);
            animals[index] = currAnimal;
            index += 1;
        }

        SimulationTestEngine engine = new SimulationTestEngine(map, animals, 2);
        this.engine = engine;
        engine.run();

        return animals;
    }

    @Test
    public void multiplyTest(){
        //are animals multyplying
        Starter starter = new Starter(
                5,//        height
                5,//        width
                MapType.GLOBE,//        mapType
                5,//        startPlantNumber
                20,//        plantEnergy
                20,//        newPlants
                PlantType.EQUATOR,//        plantType
                2,//        startAnimalNumber
                5,//        startAnimalEnergy
                4,//        energyToReproduce
                3,//        energyToChild
                0,//        minimumMutation
                0,//        maximumMutation
                MutationType.LIGHT_ADJUSTMENT,//        mutationType
                4,//        genomeLength
                AnimalBehaviour.FULL_PREDISTINATION//        animalBehaviour
        );
        AbstractWorldMap map = new GlobeMap(5,5,starter);
        Vector2d[] positions = new Vector2d[]{new Vector2d(1,2),new Vector2d(3,2)};
        int[][] genoms = new int[][]{{0,0,0,0},{0,0,0,0}};
        MapDirection[] directions = new MapDirection[]{MapDirection.E, MapDirection.W};
        Animal[] animals = runEngine(starter, map, 2, positions, genoms, directions);

        assertTrue(map.getAnimals().get(new Vector2d(2,2)).size() == 3);

    }

    @Test
    public void notMultiplyTest(){
        //not enough energy
        Starter starter = new Starter(
                5,//        height
                5,//        width
                MapType.GLOBE,//        mapType
                5,//        startPlantNumber
                20,//        plantEnergy
                20,//        newPlants
                PlantType.EQUATOR,//        plantType
                2,//        startAnimalNumber
                5,//        startAnimalEnergy
                30,//        energyToReproduce
                10,//        energyToChild
                0,//        minimumMutation
                0,//        maximumMutation
                MutationType.LIGHT_ADJUSTMENT,//        mutationType
                4,//        genomeLength
                AnimalBehaviour.FULL_PREDISTINATION//        animalBehaviour
        );
        AbstractWorldMap map = new GlobeMap(5,5,starter);
        Vector2d[] positions = new Vector2d[]{new Vector2d(1,2),new Vector2d(3,2)};
        int[][] genoms = new int[][]{{0,0,0,0},{0,0,0,0}};
        MapDirection[] directions = new MapDirection[]{MapDirection.E, MapDirection.W};
        Animal[] animals = runEngine(starter, map, 2, positions, genoms, directions);

        assertTrue(map.getAnimals().get(new Vector2d(2,2)).size() == 2);

    }

}
