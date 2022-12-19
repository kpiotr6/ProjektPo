package logic;

import logic.enums.MapType;
import logic.enums.MutationType;
import logic.enums.PlantType;

public class Main {
    public static void main(){
        Starter starter = new Starter(
            30,//        height
            30,//        width
            MapType.GLOBE,//        mapType
            5,//        startPlantNumber
            20,//        plantEnergy
            20,//        newPlants
            PlantType.EQUATOR,//        plantType
            5,//        startAnimalNumber
            10,//        startAnimalEnergy
            30,//        energyToReproduce
            10,//        energyToChild
            3,//        minimumMutation
            10,//        maximumMutation
            MutationType.LIGHT_ADJUSTMENT,//        mutationType
            12,//        genomeLength
            AnimalBehaviour.FULL_PREDISTINATION//        animalBehaviour
        );
    }
}
