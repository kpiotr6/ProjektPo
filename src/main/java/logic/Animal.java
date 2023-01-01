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
    protected boolean mostPopular;
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
        this.mostPopular = false;
        mutate();
    }



    public void mutate(){
        int min = map.config.getMinimumMutation();
        int max = map.config.getMaximumMutation();
        Random random = new Random();
        switch(map.config.getMutationType()){
            case RANDOMNESS:
                for(int i=0;i<random.nextInt(max+1-min)+min;i++){
                    genome[random.nextInt(genome.length)] = random.nextInt(8);
                }
                break;
            case LIGHT_ADJUSTMENT:
                for(int i=0;i<random.nextInt(max+1-min)+min;i++){
                    int r = random.nextInt(genome.length);
                    genome[r] = genome[r]+ 1-random.nextInt(2);
                    if(genome[r] == -1){
                        genome[r] = 7;
                    }
                }
                break;
        }
    }
    @Override
    public String getSource(){
        String source = "src\\main\\resources\\images\\";
        switch (mapDirection){
            case N -> source+="n.png";
            case NE -> source+="ne.png";
            case E -> source+="e.png";
            case SE -> source+="se.png";
            case S -> source+="s.png";
            case SW -> source+="sw.png";
            case W -> source+="w.png";
            case NW -> source+="nw.png";
        }
        return source;
    }

    public MapDirection getMapDirection() {
        return mapDirection;
    }
    public abstract void move();



    public void setMapDirection(MapDirection mapDirection) {
        this.mapDirection = mapDirection;
    }

    public int[] getGenome() {
        return genome;
    }


    public int getActivated() {
        return activated;
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
        this.eaten++;
    }

    public int getEaten() {
        return eaten;
    }



    public int getChildren() {
        return children;
    }



    public int getLives() {
        return lives;
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
            return -(this.getEnergy()-o2.getEnergy());
        }
        if(this.getLives()!= o2.getLives()){
            return -(this.getLives()-o2.getLives());
        }
        if(this.getChildren() != o2.getChildren()){
            return -(this.getChildren()-o2.getChildren());
        }
        return Math.random() < 0.5 ? -1 : 1;
    }
}
