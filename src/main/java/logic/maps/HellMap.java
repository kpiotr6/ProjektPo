package logic.maps;

import logic.AbstractWorldMap;
import logic.Animal;
import logic.Starter;
import logic.Vector2d;


public class HellMap extends AbstractWorldMap {
    public HellMap(int width, int height, Starter config) {
        super(width, height, config);
    }

    @Override
    public void applyMovementEffects(Animal animal, Vector2d current) {
        if(!this.canMoveTo(current)){
            current = getRandomPosition();
            animal.setEnergy(animal.getEnergy() - this.config.getEnergyToChild());
        }else{
            animal.setEnergy(animal.getEnergy() - 1);
        }

        animal.setPosition(current);
        this.newAnimals.get(current).add(animal);
    }

    private Vector2d getRandomPosition(){
        int x = rand.nextInt(this.width);
        int y = rand.nextInt(this.height);
        return new Vector2d(x,y);
    }

}