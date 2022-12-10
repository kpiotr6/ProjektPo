package logic;


import java.lang.reflect.Array;
import java.util.*;

public abstract class AbstractWorldMap{
    //final protected List<AbstractMapElement> elementsList = new LinkedList<>();
    final protected HashMap<Vector2d, Plant> plants = new HashMap<>();
    protected HashMap<Vector2d, List<Animal>> animals = new HashMap<>();
    protected HashMap<Vector2d, List<Animal>> newAnimals = new HashMap<>();
    final protected List<Animal> deadAnimals = new LinkedList<>();
    protected Starter config;
    protected int height;
    protected int width;
    public abstract Vector2d applyMovementEffects(Animal animal,Vector2d current);
    public static final Comparator<Animal> animalComparator = new Comparator<Animal>() {
        @Override
        public int compare(Animal o1, Animal o2) {
            if(o1.getEnergy()!=o2.getEnergy()){
                return o1.getEnergy()-o2.getEnergy();
            }
            if(o1.getLives()!= o2.getLives()){
                return o1.getLives()-o2.getLives();
            }
            return o1.getChildren()-o2.getChildren();
        }
    };
    public void kill(){
        for(List<Animal> e:animals.values()){
            Iterator<Animal> i = e.iterator();
            while (i.hasNext()){
                Animal el = i.next();
                if(el.isDead()){
                    i.remove();
                    deadAnimals.add(el);
                }
            }
        }
    }
    public void move(){
        newAnimals.clear();
        for (var e:animals.entrySet()) {
            List<Animal> a = e.getValue();
            for (Animal el:a) {
                el.move();
            }
        }
        HashMap<Vector2d, List<Animal>> tmp = newAnimals;
        newAnimals = animals;
        animals = tmp;
    }
    public void consume(){
        for (var e:plants.entrySet()) {
            Vector2d position = e.getKey();
            List<Animal> onPosition = animals.get(position);
            orderDomination(onPosition);
        }
    }
    public void multiplicate(){
        for (List<Animal> e:this.animals.values()) {
            List<Animal> animals = orderDomination(e);
            if(animals.size()<2 || animals.get(1).getEnergy()<config.getEnergyToReproduce()){
                return;
            }
            Animal first = animals.get(0);
            Animal second = animals.get(1);
            int[] childGenome = new int[config.getGenomeLength()];
            int firstGenes = config.getGenomeLength()*first.getEnergy()/(first.getEnergy()+ second.getEnergy());
            int secondGenes = config.getGenomeLength() - firstGenes;
            Random random = new Random();
            int side = random.nextInt(0,2);
            int[] firstGenome = first.getGenome();
            int[] secondGenome = second.getGenome();
            for (int i = side*(firstGenes+1); i < firstGenes+side*secondGenes; i++) {
                childGenome[i] = firstGenome[i];
            }
            for(int i = Math.abs(side-1)*(secondGenes+1);i < secondGenes+Math.abs(side-1)*secondGenes;i++){
                childGenome[i] = secondGenome[i];
            }
            int childEnergy = config.getEnergyToChild();
            Animal child = new Animal(first.getPosition(),childGenome,childEnergy);
            int firstEnergyDifference = childEnergy*first.getEnergy()/(first.getEnergy()+ second.getEnergy());
            int secondEnergyDifference = childEnergy-firstEnergyDifference;
            first.giveBirth(firstEnergyDifference);
            second.giveBirth(secondEnergyDifference);
            e.add(child);
        }
    }
    public List<Animal> orderDomination(List<Animal> singleField){
        List<Animal> animals = new LinkedList<>();
        Animal first = null;
        Animal second = null;
        int minimumEnergy = config.getEnergyToReproduce();
        for (Animal a: singleField) {
            animals.add(a);

        }
        if(animals.size()==0){
            return null;
        }
        animals.sort(animalComparator);
        return animals;
    }

}
