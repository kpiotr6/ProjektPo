package logic.gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.AbstractWorldMap;
import logic.Animal;
import logic.Starter;

import java.util.Arrays;

public class AnimalStatisticFiller implements Runnable{

    private AbstractWorldMap map;
    private Animal animal;
    private VBox allStatistics;
    private Starter config;

    public AnimalStatisticFiller(AbstractWorldMap map, VBox allStatistics, Starter starter, Animal animal) {
        this.map = map;
        this.allStatistics = allStatistics;
        this.config = starter;
        this.animal = animal;
    }

    public void fillLabels() {
        var children = allStatistics.getChildren();
        int index = 0;
        for (Node child : children) {
            String text = "";
            switch (index) {
                case 0:
                    text = Arrays.toString(animal.getGenome());
                    break;
                case 1:
                    text = String.valueOf(animal.getActivated());
                    break;
                case 2:
                    text = String.valueOf(animal.getEnergy());
                    break;
                case 3:
                    text = String.valueOf(animal.getChildren());
                    break;
                case 4:
                    text = String.valueOf(animal.getEaten());
                    break;
                case 5:
                    if(!animal.isDead()) text = String.valueOf(animal.getLives());
                    else text = "0";
                    break;
                case 6:
                    if(animal.isDead()) text = String.valueOf(animal.getLives());
                    else text = "0";
                    break;
                default:
                    break;
            }
            ((Label) (((HBox) child).getChildren()).get(1)).setText(text);
            index += 1;
        }
    }

    public void eraseLabels(){
        var children = allStatistics.getChildren();
        int index = 0;
        for (Node child : children) {
            String text = "";
            ((Label) (((HBox) child).getChildren()).get(1)).setText(text);
            index += 1;
        }
    }

    @Override
    public void run() {
        fillLabels();
    }
}
