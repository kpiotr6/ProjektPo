package logic;

import logic.enums.PlantType;

import java.util.Random;

public abstract class Plantator {
    protected AbstractWorldMap map;
    Random rand = new Random();

    public Plantator(AbstractWorldMap map){
        this.map = map;
    }
    public abstract void plant();

    public boolean isSpecialPlant(){
        return this.map.generateNumber(0,9) < 2;
    }
}
