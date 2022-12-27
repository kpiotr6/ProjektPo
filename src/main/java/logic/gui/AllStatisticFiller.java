package logic.gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.AbstractWorldMap;
import logic.Starter;

public class AllStatisticFiller implements Runnable{

    private AbstractWorldMap map;
    private VBox allStatistics;
    private Starter config;

    public AllStatisticFiller(AbstractWorldMap map, VBox allStatistics, Starter starter) {
        this.map = map;
        this.allStatistics = allStatistics;
        this.config = starter;
    }

    private void fillLabels(){
        var children = allStatistics.getChildren();
        int index = 0;
        String[] genoms = map.getMostPopularGenotypes();
        for (Node child: children) {
            if(child instanceof HBox){
                String text = "";
                switch(index){
                    case 0:
                        text = String.valueOf((map.getAnimalCount()));
                        break;
                    case 1:
                        text = String.valueOf((map.getPlantCount()));
                        break;
                    case 2:
                        text = String.valueOf((map.getEmptyFileds()));
                        break;
                    case 3:
                        text = String.valueOf((map.getEnergyAverage()));
                        break;
                    case 4:
                        text = String.valueOf((map.getLifeLengthAverage()));
                        break;
                    default:
                        break;
                }
                ((Label)(((HBox) child).getChildren()).get(1)).setText(text);
            }else if(index >= 6){
                ((Label)child).setText(genoms[index-6]);
            }
            index += 1;
        }
    }

    public void run(){
        fillLabels();
    }
}
