import logic.*;
import logic.enums.*;
import logic.maps.GlobeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsumeTest {
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

        animals[0].setEnergy(10);

        SimulationTestEngine engine = new SimulationTestEngine(map, animals, 4);
        this.engine = engine;
        engine.run();

        return animals;
    }

    @Test
    public void consumeTest(){
        //are animals multyplying
        Starter starter = new Starter(
                5,//        height
                5,//        width
                MapType.GLOBE,//        mapType
                25,//        startPlantNumber
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
        Vector2d[] positions = new Vector2d[]{new Vector2d(2,2),new Vector2d(2,2)};
        int[][] genoms = new int[][]{{0,0,0,0},{0,0,0,0}};
        MapDirection[] directions = new MapDirection[]{MapDirection.E, MapDirection.W};
        Animal[] animals = runEngine(starter, map, 2, positions, genoms, directions);

        System.out.println(animals[0].getEnergy());
        System.out.println(animals[1].getEnergy());
        assertTrue(animals[0].getEnergy() > 5);
        assertTrue(animals[1].getEnergy() == 5);

    }
}
