package logic.gui;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import logic.*;
import logic.simulation.SimulationEngine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GridFiller implements Runnable{
    private AbstractWorldMap map;
    private GridPane grid;
    private Starter config;
    private List<MapElementRepresentation> elementRepresentation;
    private SimulationEngine engine;
    public GridFiller(AbstractWorldMap map, GridPane grid, Starter starter, SimulationEngine engine){
        this.map = map;
        this.grid = grid;
        this.config = starter;
        this.elementRepresentation = new LinkedList<>();
        this.engine = engine;
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
                    if(o instanceof Animal){
                        img.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                engine.startTracking((Animal)o);
                                event.consume();
                            }
                        });
                    }

                    img.setFitHeight(grid.getRowConstraints().get(0).getPrefHeight());
                    img.setFitWidth(grid.getRowConstraints().get(0).getPrefHeight());
                    grid.add(img,x,config.getHeight()-y);
                }

            }
        }
        grid.setGridLinesVisible(false);
        grid.setGridLinesVisible(true);
    }
    public void run(){
        fillGrid();
    }
}
