package logic;

import logic.enums.MapType;
import logic.enums.MutationType;
import logic.enums.PlantType;

public class Starter {
    private int height;
    private int width;
    private MapType mapType;
    private int startPlantNumber;
    private int plantEnergy;
    private int newPlants;
    private PlantType plantType;
    private int startAnimalNumber;
    private int startAnimalEnergy;
    private int energyToReproduce;
    private int energyToChild;
    private int minimumMutation;
    private int maximumMutation;
    private MutationType mutationType;
    private int genomeLength;
    private AnimalBehaviour animalBehaviour;

    public Starter(int height, int width, MapType mapType, int startPlantNumber, int plantEnergy, int newPlants, PlantType plantType, int startAnimalNumber, int startAnimalEnergy, int energyToReproduce, int energyToChild, int minimumMutation, int maximumMutation, MutationType mutationType, int genomeLength, AnimalBehaviour animalBehaviour) {
        this.height = height;
        this.width = width;
        this.mapType = mapType;
        this.startPlantNumber = startPlantNumber;
        this.plantEnergy = plantEnergy;
        this.newPlants = newPlants;
        this.plantType = plantType;
        this.startAnimalNumber = startAnimalNumber;
        this.startAnimalEnergy = startAnimalEnergy;
        this.energyToReproduce = energyToReproduce;
        this.energyToChild = energyToChild;
        this.minimumMutation = minimumMutation;
        this.maximumMutation = maximumMutation;
        this.mutationType = mutationType;
        this.genomeLength = genomeLength;
        this.animalBehaviour = animalBehaviour;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public MapType getMapType() {
        return mapType;
    }

    public int getStartPlantNumber() {
        return startPlantNumber;
    }

    public int getPlantEnergy() {
        return plantEnergy;
    }

    public int getNewPlants() {
        return newPlants;
    }

    public PlantType getPlantType() {
        return plantType;
    }

    public int getStartAnimalNumber() {
        return startAnimalNumber;
    }

    public int getStartAnimalEnergy() {
        return startAnimalEnergy;
    }

    public int getEnergyToReproduce() {
        return energyToReproduce;
    }

    public int getEnergyToChild() {
        return energyToChild;
    }

    public int getMinimumMutation() {
        return minimumMutation;
    }

    public int getMaximumMutation() {
        return maximumMutation;
    }

    public MutationType getMutationType() {
        return mutationType;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public AnimalBehaviour getAnimalBehaviour() {
        return animalBehaviour;
    }
}
