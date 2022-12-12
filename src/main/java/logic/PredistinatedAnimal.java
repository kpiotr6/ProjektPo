package logic;

import java.util.Random;

public class PredistinatedAnimal extends Animal {
    public PredistinatedAnimal(Vector2d position, int[] genome, int energy, AbstractWorldMap map) {
        super(position, genome, energy, map);
    }
    public void move(){
        mapDirection.turn(genome[activated]);
        Random random = new Random();
        int r = random.nextInt(0,10);
        if(r<=7){
            activated = (activated+1)%genome.length;
        }
        else{
            int old = activated;
            while(old == activated) {
                activated = random.nextInt(0,genome.length);
            }
        }
        Vector2d tmpPosition = position.add(mapDirection.toUnitVector());
        map.applyMovementEffects(this,tmpPosition);
    }
}
