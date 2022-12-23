package logic.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MapElementRepresentation {
    public Starter config;
    public ImageView plant;
    public ImageView[] animals = new ImageView[8];
    public MapElementRepresentation(Starter config){
        this.config = config;
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
            if(animal.isMostPopular()){

            }
            else if(animal.getEnergy()<=startEnergy/2){

            }
            else if (animal.getEnergy()<=startEnergy) {

            }
            else{

            }
            return animals[i];

        }
    }
}
