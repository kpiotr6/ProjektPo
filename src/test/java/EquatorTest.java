import logic.*;
import logic.enums.*;
import logic.maps.GlobeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EquatorTest {

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

        SimulationTestEngine engine = new SimulationTestEngine(map, animals, 3);
        this.engine = engine;
        engine.run();

        return animals;
    }

    @Test
    public void overflowTest(){
        Starter starter = new Starter(
                5,//        height
                5,//        width
                MapType.GLOBE,//        mapType
                5,//        startPlantNumber
                20,//        plantEnergy
                20,//        newPlants
                PlantType.EQUATOR,//        plantType
                2,//        startAnimalNumber
                10,//        startAnimalEnergy
                100,//        energyToReproduce
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

        assertTrue(this.engine.year == 10);
    }
}
