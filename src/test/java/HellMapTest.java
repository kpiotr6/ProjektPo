import logic.*;
import logic.enums.MapDirection;
import logic.enums.MapType;
import logic.enums.MutationType;
import logic.enums.PlantType;
import logic.maps.HellMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HellMapTest {
    public Animal[] runEngine(Starter starter, AbstractWorldMap map, int numOfAnimals, Vector2d[] positions, int[][] genoms, MapDirection[] directions  ){
        Animal[] animals = new Animal[numOfAnimals];

        int index = 0;
        for ( Vector2d position: positions) {
            Animal currAnimal = new PredistinatedAnimal(position, genoms[index], starter.getStartAnimalEnergy(), map);
            currAnimal.setMapDirection(directions[index]);
            animals[index] = currAnimal;
            index += 1;
        }

        SimulationTestEngine engine = new SimulationTestEngine(map, animals);

        engine.run();

        return animals;
    }

    @Test
    public void sideTest(){
        Starter starter = new Starter(
                30,//        height
                30,//        width
                MapType.HELL_GATE,//        mapType
                5,//        startPlantNumber
                20,//        plantEnergy
                20,//        newPlants
                PlantType.EQUATOR,//        plantType
                5,//        startAnimalNumber
                5,//        startAnimalEnergy
                30,//        energyToReproduce
                10,//        energyToChild
                3,//        minimumMutation
                10,//        maximumMutation
                MutationType.LIGHT_ADJUSTMENT,//        mutationType
                4,//        genomeLength
                AnimalBehaviour.FULL_PREDISTINATION//        animalBehaviour
        );
        AbstractWorldMap map = new HellMap(5,5,starter);
        Vector2d[] positions = new Vector2d[]{new Vector2d(2,2),new Vector2d(2,2)};
        int[][] genoms = new int[][]{{0,0,0,0},{0,0,0,0}};
        MapDirection[] directions = new MapDirection[]{MapDirection.E, MapDirection.W};

        Animal[] animals = runEngine(starter, map, 2, positions, genoms, directions);

        assertEquals(animals[0].getEnergy(), 2- starter.getEnergyToChild());
        assertEquals(animals[1].getEnergy(), 2- starter.getEnergyToChild());
    }

    @Test
    public void polesTest(){
        Starter starter = new Starter(
                30,//        height
                30,//        width
                MapType.GLOBE,//        mapType
                5,//        startPlantNumber
                20,//        plantEnergy
                20,//        newPlants
                PlantType.EQUATOR,//        plantType
                5,//        startAnimalNumber
                5,//        startAnimalEnergy
                30,//        energyToReproduce
                10,//        energyToChild
                3,//        minimumMutation
                10,//        maximumMutation
                MutationType.LIGHT_ADJUSTMENT,//        mutationType
                4,//        genomeLength
                AnimalBehaviour.FULL_PREDISTINATION//        animalBehaviour
        );
        AbstractWorldMap map = new HellMap(5,5,starter);
        Vector2d[] positions = new Vector2d[]{new Vector2d(2,2),new Vector2d(2,2)};
        int[][] genoms = new int[][]{{0,0,0,0},{0,0,0,0}};
        MapDirection[] directions = new MapDirection[]{MapDirection.N, MapDirection.S};

        Animal[] animals = runEngine(starter, map, 2, positions, genoms, directions);

        assertEquals(animals[0].getEnergy(), 2- starter.getEnergyToChild());
        assertEquals(animals[1].getEnergy(), 2- starter.getEnergyToChild());
    }

    @Test
    public void cornerTest(){
        Starter starter = new Starter(
                30,//        height
                30,//        width
                MapType.GLOBE,//        mapType
                5,//        startPlantNumber
                20,//        plantEnergy
                20,//        newPlants
                PlantType.EQUATOR,//        plantType
                5,//        startAnimalNumber
                5,//        startAnimalEnergy
                30,//        energyToReproduce
                10,//        energyToChild
                3,//        minimumMutation
                10,//        maximumMutation
                MutationType.LIGHT_ADJUSTMENT,//        mutationType
                4,//        genomeLength
                AnimalBehaviour.FULL_PREDISTINATION//        animalBehaviour
        );
        AbstractWorldMap map = new HellMap(5,5,starter);
        Vector2d[] positions = new Vector2d[]{new Vector2d(2,2),new Vector2d(2,2)};
        int[][] genoms = new int[][]{{0,0,0,0},{0,0,0,0}};
        MapDirection[] directions = new MapDirection[]{MapDirection.NE, MapDirection.SW};

        Animal[] animals = runEngine(starter, map, 2, positions, genoms, directions);

        assertEquals(animals[0].getEnergy(), 2- starter.getEnergyToChild());
        assertEquals(animals[1].getEnergy(), 2- starter.getEnergyToChild());
    }
}
