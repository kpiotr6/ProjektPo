package logic.simulation;

import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import logic.*;
import logic.enums.AnimalBehaviour;
import logic.gui.AllStatisticFiller;
import logic.gui.AnimalStatisticFiller;
import logic.gui.GridFiller;
import logic.maps.GlobeMap;
import logic.maps.HellMap;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SimulationEngine implements Runnable{
    private AbstractWorldMap map;
    private GridFiller gridFiller;
    private AllStatisticFiller statisticFiller;
    private AnimalStatisticFiller animalStatisticFiller;
    private VBox animalStatistic;
    private Starter config;
    public volatile boolean paused = false;
    public boolean tracking = false;
    private final Object pauseLock = new Object();

//    public SimulationEngine(AbstractWorldMap map, Vector2d[] positions, int[][] genoms, Starter config){
//        int index = 0;
//        for ( Vector2d position: positions) {
//            Animal currAnimal;
//            if (config.getAnimalBehaviour() == AnimalBehaviour.FULL_PREDISTINATION){
//                currAnimal = new MadAnimal(position, genoms[index], config.getStartAnimalEnergy(), map);
//            }
//            else{
//                currAnimal = new PredistinatedAnimal(position, genoms[index], config.getStartAnimalEnergy(), map);
//            }
//            map.initAnimal(currAnimal);
//            index += 1;
//        }
//        this.map = map;
//    }
    public AbstractWorldMap getMap(){
        return map;
    }
    public SimulationEngine(Starter starter, GridPane grid, VBox allStatistics, VBox animalStatistic){
        this.map = switch (starter.getMapType()){
            case GLOBE -> new GlobeMap(starter.getHeight(),starter.getWidth(),starter);
            case HELL_GATE -> new HellMap(starter.getHeight(),starter.getWidth(),starter);
        };
        for(int i=0;i<starter.getStartAnimalNumber();i++){
            Animal a = animalGenerator(starter,this.map);
            this.map.initAnimal(a);
        }
        gridFiller = new GridFiller(this.map,grid,starter, this);
        this.statisticFiller = new AllStatisticFiller(map, allStatistics, starter);
        this.animalStatistic = animalStatistic;
        this.config = starter;
    }
    private Animal animalGenerator(Starter starter,AbstractWorldMap tmpMap){
        Random random = new Random();
        Vector2d position = new Vector2d(random.nextInt(starter.getWidth()), random.nextInt(starter.getHeight()));
        int[] genome = new int[starter.getGenomeLength()];
        for(int i=0;i<genome.length;i++){
            genome[i] = random.nextInt(8);
        }
        return switch (starter.getAnimalBehaviour()){
            case FULL_PREDISTINATION -> new PredistinatedAnimal(position,genome,starter.getStartAnimalEnergy(),tmpMap);
            case A_LITTLE_BIT_OF_MADNESS -> new MadAnimal(position,genome,starter.getStartAnimalEnergy(),tmpMap);
        };
    }

    public void startTracking(Animal animal){
        if(!paused) return;
        animalStatisticFiller = new AnimalStatisticFiller(map, this.animalStatistic, this.config, animal);
        tracking = true;
    }
    public void run(){

        this.map.grow();

        while (this.map.getAnimalCount() > 0){
//            System.out.println(this.map.getAnimalCount());
//            System.out.print(this.map);
            this.map.kill();
            this.map.move();
            this.map.consume();
            this.map.multiplicate();
            this.map.grow();
//            this.gridFiller.fillGrid();
            Platform.runLater(this.gridFiller);
            Platform.runLater(this.statisticFiller);
            if(this.tracking){
                Platform.runLater(this.animalStatisticFiller);
            }
            //System.out.println(map.getAnimalCount());
            try{
                TimeUnit.MILLISECONDS.sleep(200);
            }catch (Exception e){
                System.out.println(e);
            }
            synchronized (pauseLock) {
                if (paused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException ex) {
                        break;
                    }
                }
            }
        }
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
}
