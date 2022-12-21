package logic;

import logic.enums.MapType;
import logic.enums.MutationType;
import logic.enums.PlantType;
import logic.maps.GlobeMap;
import logic.simulation.SimulationEngine;

public class Main {
    public static void main(String[] args){
        Starter starter = new Starter(
            30,//        height
            30,//        width
            MapType.GLOBE,//        mapType
            5,//        startPlantNumber
            20,//        plantEnergy
            5,//        newPlants
            PlantType.TOXIC_CORPSES,//        plantType
            5,//        startAnimalNumber
            100,//        startAnimalEnergy
            30,//        energyToReproduce
            10,//        energyToChild
            3,//        minimumMutation
            10,//        maximumMutation
            MutationType.LIGHT_ADJUSTMENT,//        mutationType
            4,//        genomeLength
            AnimalBehaviour.FULL_PREDISTINATION//        animalBehaviour
        );
        AbstractWorldMap map = new GlobeMap(30,30,starter);
        try{
            SimulationEngine engine = new SimulationEngine(map, new Vector2d[]{new Vector2d(2,2), new Vector2d(3,3)}, new int[][]{{0,0,0,0},{0,0,0,0}}, starter);
            engine.run();
        }
        catch(Exception e){
            System.out.println("aa");
            System.out.println(e);
            System.out.println("aa");
        }


    }
}
