package logic;

import java.util.Random;

public class MadAnimal extends Animal {
    public MadAnimal(Vector2d position, int[] genome, int energy, AbstractWorldMap map) {
        super(position, genome, energy, map);
    }
    public void move(){
        lives++;
        this.mapDirection = this.mapDirection.turn(genome[activated]);
        Random random = new Random();
        int r = random.nextInt(10);
        if(r<=7){
            activated = (activated+1)%genome.length;
        }
        else{
            int old = activated;
            while(old == activated && genome.length != 1) {
                activated = random.nextInt(genome.length);
            }
        }
        Vector2d oldPosition = position;
        Vector2d tmpPosition = position.add(mapDirection.toUnitVector());
        this.map.applyMovementEffects(this,oldPosition,tmpPosition);
    }
}
