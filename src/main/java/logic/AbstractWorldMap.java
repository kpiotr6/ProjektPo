package logic;


import java.util.*;


public abstract class AbstractWorldMap{

    protected HashMap<Vector2d, Plant> plants = new HashMap<>();
    protected HashMap<Vector2d, Plant> newPlants = new HashMap<>();
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
    private int totalDeathAnimalLength;
    private int deathAnimalCount;//With dead ones
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
        this.deathAnimalCount = 0;
        this.totalDeathAnimalLength = 0;
        plantator.startPlants();
    }

    public int getAnimalCount() {
        return animalCount;
    }
    public int getPlantCount(){return plantator.getPlantNum();}
    public int getEmptyFileds(){
        int count = 0;
        for(int x=0;x<config.getWidth();x++) {
            for (int y = 0; y < config.getHeight(); y++) {
                Object o = this.objectAt(new Vector2d(x, y));
                if(o == null) count += 1;
            }
        }
        return count;
    }

    public float getEnergyAverage(){
        int energySum = 0;
        for(var e:animals.values()){
            Iterator<Animal> i = e.iterator();
            while (i.hasNext()){
                Animal el = i.next();
                energySum += el.getEnergy();
            }
        }
        if(this.getAnimalCount() == 0) return 0;
        return energySum/this.getAnimalCount();
    }

    public int getLifeLengthAverage(){
        if(this.deathAnimalCount == 0) return 0;
        return totalDeathAnimalLength/deathAnimalCount;
    }
    public String[] getMostPopularGenotypes(){
        HashMap<String, Integer> CountHashMap = new HashMap<>();
        String[] ans = new String[]{"","","","",""};

        for(var e:animals.values()){
            Iterator<Animal> i = e.iterator();
            while (i.hasNext()){
                Animal el = i.next();
                String genotype = Arrays.toString(el.getGenome());
                CountHashMap.put(genotype, CountHashMap.getOrDefault(genotype, 0) + 1);
            }
        }

        List<Integer> allCounts = new ArrayList<Integer>(CountHashMap.values());
        Collections.sort(allCounts, Collections.reverseOrder());
        List<Integer> top5 = allCounts.subList(0, allCounts.size() < 5 ? allCounts.size() : 5);

        for (var e:CountHashMap.entrySet()) {
            if(top5.contains(e.getValue())){
                int id =top5.indexOf(e.getValue());
                ans[id] = e.getKey();
                top5.set(id, -1);
            }
        }
        return ans;
    }

    public boolean canMoveTo(Vector2d position) {
        return lowerLeft.precedes(position) && upperRight.follows(position);
    }

    public Plant plantAt(Vector2d position){
        return plants.get(position);
    }

    public Animal animalAt(Vector2d position){
        if(animals.get(position) == null) return null;
        if(animals.get(position).size()==0) return null;
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
                    totalDeathAnimalLength += el.lives;
                    deathAnimalCount++;
                    deadAnimals.add(el);
                    this.animalCount -=1;
                }else{
                    el.lives++;
                }
            }
        }
    }
    public void move(){
        newAnimals.clear();
        for (var e:animals.entrySet()) {
            var a = e.getValue();
            for (Animal el:a) {
                System.out.println("start");
                el.move();
                System.out.println("stop");
            }
        }
        var tmp = newAnimals;
        newAnimals = animals;
        animals = tmp;
    }
    public void consume(){
        newPlants.clear();
        for (var e:plants.entrySet()) {
            Vector2d position = e.getKey();
            var onPosition = animals.get(position);
            if(onPosition == null || animals.size()==0){
                newPlants.put(position, e.getValue());
                continue;
            }
            Animal strongest = onPosition.first();
            strongest.eat(config.getPlantEnergy());
            plantator.plantEaten(position);
        }
        var tmp = newPlants;
        newPlants = plants;
        plants = tmp;
    }
    public void multiplicate(){
        for (var e:this.animals.values()) {
            if(e.size()<2 || e.first().getEnergy()<config.getEnergyToReproduce()){
                return;
            }
            Iterator<Animal> it = e.iterator();
            Animal first = it.next();
            Animal second = it.next();
            int[] childGenome = new int[config.getGenomeLength()];
            int firstGenes = config.getGenomeLength()*first.getEnergy()/(first.getEnergy()+ second.getEnergy());
//            if(firstGenes == 0){
//               firstGenes = 1;
//            }
//            if(firstGenes == config.getGenomeLength()){
//                firstGenes = config.getGenomeLength()-1;
//            }
            int secondGenes = config.getGenomeLength() - firstGenes;
            Random random = new Random();

            int side = random.nextInt(2);
            int[] firstGenome = first.getGenome();
            int[] secondGenome = second.getGenome();

            for (int i = side*(firstGenes+1); i < firstGenes+side*secondGenes; i++) {
                childGenome[i] = firstGenome[i];
            }
            for(int i = Math.abs(side-1)*(secondGenes+1);i < secondGenes+Math.abs(side-1)*firstGenes;i++){
                childGenome[i] = secondGenome[i];
            }
            int childEnergy = config.getEnergyToChild();
            Animal child =
            switch (config.getAnimalBehaviour()){
                case FULL_PREDISTINATION -> new PredistinatedAnimal(first.getPosition(),childGenome,childEnergy,this);
                case A_LITTLE_BIT_OF_MADNESS ->  new MadAnimal(first.getPosition(),childGenome,childEnergy,this);
            };
            int firstEnergyDifference = childEnergy*first.getEnergy()/(first.getEnergy()+ second.getEnergy());
            int secondEnergyDifference = childEnergy-firstEnergyDifference;
            first.giveBirth(firstEnergyDifference);
            second.giveBirth(secondEnergyDifference);
            e.add(child);
            animalCount++;
        }
    }
    public void grow(){
        plantator.plant();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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
        this.animalCount++;
    }

    @Override
    public String toString() {
        return drawingModule.draw(this.lowerLeft, this.upperRight);
    }
}
