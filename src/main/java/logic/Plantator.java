package logic;

import logic.enums.PlantType;

import java.util.Random;

public abstract class Plantator {
    protected int specialPlantsNum = 0;
    protected int normalPlantsNum = 0;
    protected AbstractWorldMap map;
    Random rand = new Random();
    int specialFields;

    public Plantator(AbstractWorldMap map){
        this.map = map;
        this.specialFields = (int)Math.ceil(this.map.height*this.map.width*0.2);
    }
    public abstract void plant();
    public abstract boolean isSpecialPlant();
    public abstract boolean isSpecialField(Vector2d position);
    public abstract void plantEaten(Vector2d position);

    public int getPlantNum(){
        return  specialPlantsNum + normalPlantsNum;
    }
}
