package logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractMapElement {
    private MapDirection mapDirection;
    private AbstractWorldMap map;
    private int[] genome;
    private int activated;
    private int energy;
    private int eaten;
    private int children;
    private int lives;
    private int died;
    private boolean dead;

    public Animal(Vector2d position,int[] genome,int energy){
        super(position);
        Random random = new Random();
        this.mapDirection = MapDirection.fromNumber(random.nextInt(8));
        this.genome = genome;
        this.activated = random.nextInt(genome.length);
        this.energy = energy;
        this.eaten = 0;
        this.children = 0;
        this.lives = 0;
        this.died = -1;
        this.dead = false;
    }
    @Override
    public String getSource(){
        String source = "src/main/resources/";
        switch (mapDirection){
            case N -> source+="up.png";
            case NE -> source+="upright.png";
            case E -> source+="right.png";
            case SE -> sourced+="downright.png";
            case S -> source+="down.png";
            case SW -> source+="downleft.png";
            case W -> source+="left.png";
            case NW -> source+="upleft.png";
        }
        return source;
    }

    public MapDirection getMapDirection() {
        return mapDirection;
    }
    public AnimalData createAnimalData(){
        return new AnimalData(
                this.genome,
                this.activated,
                this.energy,
                this.eaten,
                this.children,
                this.lives,
                this.died
        );
    }
    public void move(){
        mapDirection.turn(genome[activated]);
        Vector2d tmpPosition = position.add(mapDirection.toUnitVector());
        map.applyMovementEffects(this,tmpPosition);
    }

    public void setMapDirection(MapDirection mapDirection) {
        this.mapDirection = mapDirection;
    }

    public int[] getGenome() {
        return genome;
    }

    public void setGenome(int[] genome) {
        this.genome = genome;
    }

    public int getActivated() {
        return activated;
    }

    public void setActivated(int activated) {
        this.activated = activated;
    }

    public int getEnergy() {
        return energy;
    }
    public void giveBirth(int energy){
        this.energy -= energy;
        this.children++;
    }
    public void eat(int energy){
        this.energy += energy;
    }

    public int getEaten() {
        return eaten;
    }

    public void setEaten(int eaten) {
        this.eaten = eaten;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getDied() {
        return died;
    }

    public void setDied(int died) {
        this.died = died;
    }

    public boolean isDead() {
        if(energy == 0){
            died = 0;
            this.dead = true;
            return true;
        }
        return false;

    }
}
