package logic;

import logic.enums.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

import logic.enums.MutationType;

public abstract class Animal extends AbstractMapElement implements Comparable<Animal> {
    protected MapDirection mapDirection;
    protected int[] genome;
    protected int activated;
    protected int energy;
    protected int eaten;
    protected int children;
    protected int lives;
    protected int died;
    protected boolean dead;

    public  Animal(Vector2d position,int[] genome,int energy,AbstractWorldMap map){
        super(map,position);
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
    void mutate(){
        int min = map.config.getMinimumMutation();
        int max = map.config.getMaximumMutation();
        Random random = new Random();
        switch(map.config.getMutationType()){
            case RANDOMNESS:
                for(int i=0;i<random.nextInt(min,max+1);i++){
                    genome[random.nextInt(0,genome.length)] = random.nextInt(0,8);
                }
                break;
            case LIGHT_ADJUSTMENT:
                for(int i=0;i<random.nextInt(min,max+1);i++){
                    int r = random.nextInt(0,genome.length);
                    genome[r] = genome[r]+ 1-random.nextInt(0,2);
                    if(genome[r] == -1){
                        genome[r] = 7;
                    }
                }
                break;
        }
    }
    @Override
    public String getSource(){
        String source = "src/main/resources/";
        switch (mapDirection){
            case N -> source+="up.png";
            case NE -> source+="upright.png";
            case E -> source+="right.png";
            case SE -> source+="downright.png";
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
    public abstract void move();

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

    public void setEnergy(int energy) {
        this.energy = energy;
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
        if(energy <= 0){
            died = 0;
            this.dead = true;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getMapDirection().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return activated == animal.activated && energy == animal.energy && eaten == animal.eaten && children == animal.children && lives == animal.lives && died == animal.died && dead == animal.dead && mapDirection == animal.mapDirection && map.equals(animal.map) && Arrays.equals(genome, animal.genome);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mapDirection, map, activated, energy, eaten, children, lives, died, dead);
        result = 31 * result + Arrays.hashCode(genome);
        return result;
    }

    public int compareTo(Animal o2){
        if(this.getEnergy()!=o2.getEnergy()){
            return this.getEnergy()-o2.getEnergy();
        }
        if(this.getLives()!= o2.getLives()){
            return this.getLives()-o2.getLives();
        }
        if(this.getChildren() != o2.getChildren()){
            return this.getChildren()-o2.getChildren();
        }
        return Math.random() < 0.5 ? -1 : 1;
    }
}
