package logic;

import javafx.application.Application;
import logic.enums.MapType;
import logic.enums.MutationType;
import logic.enums.PlantType;
import logic.gui.App;
import logic.maps.GlobeMap;
import logic.simulation.SimulationEngine;

import java.util.Random;

public class Main {
    public static void main(String[] args){
        Application.launch(App.class,args);
        Starter starter = new Starter(
            30,//        height
            30,//        width
            MapType.GLOBE,//        mapType
            5,//        startPlantNumber
            20,//        plantEnergy

            30,//        newPlants
            PlantType.TOXIC_CORPSES,//        plantType
            5,//        startAnimalNumber
            20,//        startAnimalEnergy
            5,//        energyToReproduce
            10,//        energyToChild
            3,//        minimumMutation
            10,//        maximumMutation
            MutationType.RANDOMNESS,//        mutationType
            4,//        genomeLength
            AnimalBehaviour.A_LITTLE_BIT_OF_MADNESS//        animalBehaviour
        );


        AbstractWorldMap map = new GlobeMap(10,10,starter);

        try{
            Vector2d[] vector2ds = new Vector2d[starter.getStartAnimalNumber()];
            Random random = new Random();
            int[][] genoms = new int[starter.getStartAnimalNumber()][starter.getGenomeLength()];
            for(int i=0;i<vector2ds.length;i++){
                vector2ds[i] = new Vector2d(random.nextInt(map.width),random.nextInt(map.height) );
                genoms[i] = new int[]{0, 0, 0, 0};
            }
            SimulationEngine engine = new SimulationEngine(map, vector2ds, genoms, starter);
            engine.run();
        }
        catch(Exception e){
            System.out.println("aa");
            System.out.println(e);
            e.printStackTrace();
            System.out.println("aa");
        }


    }
}
