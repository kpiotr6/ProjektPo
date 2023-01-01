import logic.*;
import logic.enums.*;
import logic.maps.GlobeMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrowTest {

    SimulationTestEngine engine;

    public Animal[] runEngine(Starter starter, AbstractWorldMap map, int numOfAnimals, Vector2d[] positions, int[][] genoms, MapDirection[] directions, int param ){
        Animal[] animals = new Animal[numOfAnimals];

        int index = 0;
        for ( Vector2d position: positions) {
            Animal currAnimal = new PredistinatedAnimal(position, genoms[index], starter.getStartAnimalEnergy(), map);
            currAnimal.setMapDirection(directions[index]);
            animals[index] = currAnimal;
            index += 1;
        }
        SimulationTestEngine engine = new SimulationTestEngine(map, animals, param);

        this.engine = engine;
        engine.run();

        return animals;
    }

    @Test
    public void growTestEquator(){
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
        Animal[] animals = runEngine(starter, map, 2, positions, genoms, directions,5);

        assertTrue(map.getPlantCount() == 25);
    }

    @Test
    public void growTestToxic(){
        Starter starter = new Starter(
                5,//        height
                5,//        width
                MapType.GLOBE,//        mapType
                25,//        startPlantNumber
                20,//        plantEnergy
                20,//        newPlants
                PlantType.TOXIC_CORPSES,//        plantType
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
        Animal[] animals = runEngine(starter, map, 2, positions, genoms, directions,5);

        assertTrue(map.getPlantCount() == 25);
    }

    @Test
    public void growPremiumFieldsTest(){
        Starter starter = new Starter(
                10,//        height
                10,//        width
                MapType.GLOBE,//        mapType
                15,//        startPlantNumber
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
        AbstractWorldMap map = new GlobeMap(10,10,starter);
        Vector2d[] positions = new Vector2d[]{new Vector2d(2,2),new Vector2d(2,2)};
        int[][] genoms = new int[][]{{0,0,0,0},{0,0,0,0}};
        MapDirection[] directions = new MapDirection[]{MapDirection.E, MapDirection.W};
        Animal[] animals = runEngine(starter, map, 2, positions, genoms, directions,5);
        Plantator plantator = map.getPlantator();
        assertTrue(plantator.getSpecialPlantsNum() > plantator.getPlantNum()-plantator.getSpecialPlantsNum());
    }
}
