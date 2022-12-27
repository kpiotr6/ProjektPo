package logic.gui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.AbstractMapElement;
import logic.AbstractWorldMap;
import logic.Starter;
import logic.Vector2d;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GridFiller implements Runnable{
    private AbstractWorldMap map;
    private GridPane grid;
    private Starter config;
    private List<MapElementRepresentation> elementRepresentation;
    private List<Label> allStats;
    public GridFiller(AbstractWorldMap map, GridPane grid, List<Label> allstats, Starter starter){
        this.map = map;
        this.grid = grid;
        this.config = starter;
        this.elementRepresentation = new LinkedList<>();
        this.allStats = allstats;
    }
    public void fillGrid(){
        grid.getChildren().clear();
        ListIterator<MapElementRepresentation> iterator = elementRepresentation.listIterator();

        for(int x=0;x<config.getWidth();x++){
            for(int y=0;y<config.getHeight();y++){
                Object o = map.objectAt(new Vector2d(x,y));
                if(o!=null){
                    MapElementRepresentation e;
                    if(iterator.hasNext()){
                        e = iterator.next();
                    }
                    else{
                        new MapElementRepresentation(config);
                        iterator.add(new MapElementRepresentation(config));
                        e = iterator.previous();
                        iterator.next();
                    }
                    ImageView img = e.getRepresentation((AbstractMapElement)o);
                    img.setFitHeight(grid.getRowConstraints().get(0).getPrefHeight());
                    img.setFitWidth(grid.getRowConstraints().get(0).getPrefHeight());
                    grid.add(img,x,config.getHeight()-y);
                }

            }
        }
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);
    }
    public void fillStats(){
        allStats.get(0).setText(map.getAnimalCount()+"");
        allStats.get(1).setText(map.getPlantNumber()+"");
        int k =0;
        for(int i=0;i<map.getWidth();i++){
            for(int j=0;j<map.getHeight();j++){
                if(map.isOccupied(new Vector2d(i,j))){
                    k++;
                }
            }
        }
        allStats.get(2).setText(map.getWidth()*map.getHeight()-k+"");
        allStats.get(3).setText(map.getAverageEnergy()+"");
        allStats.get(4).setText(map.getAverageLifespan()+"");
    }
    public void run(){
        fillGrid();
        fillStats();
    }
}
