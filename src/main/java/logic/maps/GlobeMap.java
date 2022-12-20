package logic.maps;

import logic.AbstractWorldMap;
import logic.Animal;
import logic.Starter;
import logic.Vector2d;

public class GlobeMap extends AbstractWorldMap {

    public GlobeMap(int height, int width, Starter config) {
        super(height, width, config);
    }

    @Override
    public void applyMovementEffects(Animal animal,Vector2d old, Vector2d current) {
        Vector2d position = current;
        if(!this.canMoveTo(current)){
            if(current.y >= this.height || current.y < 0) {
                animal.setMapDirection(animal.getMapDirection().turn(4));
                position = old;
            }
            else if (current.x >= width || current.x < 0) {
                position = new Vector2d(current.x < 0 ? this.width-1 : 0,  current.y);
                animal.setPosition(position);
            };
        }
        else animal.setPosition(current);

        animal.setEnergy(animal.getEnergy() - 1);
        this.addToNewAnimals(animal, position);
    }

}
