package logic;

public class PredistinatedAnimal extends Animal{
    public PredistinatedAnimal(Vector2d position, int[] genome, int energy, AbstractWorldMap map) {
        super(position, genome, energy, map);
    }
    public void move(){

        this.mapDirection =  mapDirection.turn(genome[activated]);
        activated = (activated+1)%genome.length;

        Vector2d oldPosition = position;
        Vector2d tmpPosition = position.add(mapDirection.toUnitVector());
        map.applyMovementEffects(this,oldPosition,tmpPosition);

    }
}
