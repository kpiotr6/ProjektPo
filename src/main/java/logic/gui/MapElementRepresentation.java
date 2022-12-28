package logic.gui;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.*;
import logic.simulation.SimulationEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MapElementRepresentation {
    public Starter config;
    public ImageView plant;
    public ImageView[] animals = new ImageView[8];
    public SimulationEngine engine;
    public MapElementRepresentation(Starter config, SimulationEngine engine){
        this.config = config;
        this.engine = engine;
    }
    public ImageView getRepresentation(AbstractMapElement element){
        if(element instanceof Plant){
            if(plant == null){
                try {
                    plant = new ImageView(new Image(new FileInputStream(element.getSource())));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            return plant;
        }
        else{
            Animal animal = (Animal)element;
            int i = animal.getMapDirection().getOrder();
            if(animals[i]==null){
                try {
                    animals[i] = new ImageView(new Image(new FileInputStream(animal.getSource())));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            int startEnergy = config.getStartAnimalEnergy();
            ColorAdjust adjust = new ColorAdjust();
            if(engine.trackedAnimal==element){
                adjust.setHue(1);
            }
            else if(animal.getEnergy()<=startEnergy/2){
                adjust.setHue(-0.5);
            }
            else if (animal.getEnergy()<=startEnergy) {
                adjust.setHue(-0.1);
            }
            else{
                adjust.setHue(0.5);
            }
            animals[i].setEffect(adjust);
            return animals[i];

        }
    }
}
