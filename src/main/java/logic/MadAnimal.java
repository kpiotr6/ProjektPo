package logic;

public class MadAnimal extends Animal{
    public MadAnimal(Vector2d position, int[] genome, int energy, AbstractWorldMap map) {
        super(position, genome, energy, map);
    }
    public void move(){
        mapDirection.turn(genome[activated]);
        activated = (activated+1)%genome.length;
        Vector2d tmpPosition = position.add(mapDirection.toUnitVector());
        map.applyMovementEffects(this,tmpPosition);
    }
}
