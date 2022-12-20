package logic;


import java.util.*;

public abstract class AbstractWorldMap{

    final protected HashMap<Vector2d, Plant> plants = new HashMap<>();
    protected HashMap<Vector2d, SortedSet<Animal>> animals = new HashMap<>();
    protected HashMap<Vector2d, SortedSet<Animal>> newAnimals = new HashMap<>();
    //protected HashMap<Vector2d,Integer> toxic = new HashMap<>();
    final protected List<Animal> deadAnimals = new LinkedList<>();
    protected Starter config;
    protected Plantator plantator;
    protected int height;
    protected int width;
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    private int animalCount;
    private MapVisualizer drawingModule = new MapVisualizer(this);
    protected Random rand = new Random();
    public abstract void applyMovementEffects(Animal animal,Vector2d old,Vector2d current);

    public AbstractWorldMap(int height,int width, Starter config){
         this.height = height;
         this.width = width;
         this.config = config;
         switch (config.getPlantType()){
             case EQUATOR:
                 plantator = new EquatorPlantator(this);
                 break;
             case TOXIC_CORPSES:
                 plantator = new ToxicPlantator(this);
                 break;
         }
         this.lowerLeft = new Vector2d(0,0);
         this.upperRight = new Vector2d(width-1, height-1);

    }

    public int getAnimalCount() {
        return animalCount;
    }

    public boolean canMoveTo(Vector2d position) {
        return lowerLeft.precedes(position) && upperRight.follows(position);
    }

    public Plant plantAt(Vector2d position){
        return plants.get(position);
    }

    public Animal animalAt(Vector2d position){
        if(animals.get(position) == null) return null;
        return animals.get(position).first();
    }

    public boolean isOccupied(Vector2d position){
        return animalAt(position) != null || plantAt(position) != null;
    }

    public Object objectAt(Vector2d position){
        if(animalAt(position) != null){
            return animalAt(position);
        }
        if(plantAt(position) != null){
            return plantAt(position);
        }
        return null;
    }
    //TODO: returns ony first animal

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

    public int generateNumber(int min, int max){
        return this.rand.nextInt(max - min + 1) + min;
    }
    public void kill(){
        for(var e:animals.values()){
            Iterator<Animal> i = e.iterator();
            while (i.hasNext()){
                Animal el = i.next();
                if(el.isDead()){
                    i.remove();
                    deadAnimals.add(el);
                    this.animalCount -=1;
                }
            }
        }
    }
    public void move(){
        newAnimals.clear();
        for (var e:animals.entrySet()) {
            var a = e.getValue();
            for (Animal el:a) {
                el.move();
            }
        }
        var tmp = newAnimals;
        newAnimals = animals;
        animals = tmp;
    }
    public void consume(){
        for (var e:plants.entrySet()) {
            Vector2d position = e.getKey();
            var onPosition = animals.get(position);
            if(animals.size()==0){
                continue;
            }
            Animal strongest = animals.get(onPosition).first();
            strongest.eat(config.getPlantEnergy());
            plants.remove(position);
        }
    }
    public void multiplicate(){
        for (var e:this.animals.values()) {
            if(e.size()<2 || e.first().getEnergy()<config.getEnergyToReproduce()){
                return;
            }
            Animal first = e.first();
            e.remove(first);
            Animal second = e.first();
            e.remove(second);
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
            Animal child =
            switch (config.getAnimalBehaviour()){
                case A_LITTLE_BIT_OF_MADNESS -> new MadAnimal(first.getPosition(),childGenome,childEnergy,this);
                case FULL_PREDISTINATION ->  new PredistinatedAnimal(first.getPosition(),childGenome,childEnergy,this);
            };
            int firstEnergyDifference = childEnergy*first.getEnergy()/(first.getEnergy()+ second.getEnergy());
            int secondEnergyDifference = childEnergy-firstEnergyDifference;
            first.giveBirth(firstEnergyDifference);
            second.giveBirth(secondEnergyDifference);
            e.add(child);
            e.add(first);
            e.add(second);
        }
    }
    public void grow(){
        plantator.plant();
    }

    protected void addToAnimals(Animal animal, Vector2d position){
        this.animals.computeIfAbsent(animal.getPosition(), k -> new TreeSet<Animal>());
        this.animals.get(position).add(animal);
    }

    protected void addToNewAnimals(Animal animal, Vector2d position){
        this.newAnimals.computeIfAbsent(animal.getPosition(), k -> new TreeSet<Animal>());
        this.newAnimals.get(position).add(animal);
    }

    protected void addPlant(Plant plant, Vector2d position){
        this.plants.put(position, plant);
    }

    public void initAnimal(Animal animal){
        this.addToAnimals(animal, animal.getPosition());
        this.animalCount += 1;
    }

    @Override
    public String toString() {
        return drawingModule.draw(this.lowerLeft, this.upperRight);
    }
}
