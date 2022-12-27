package logic.gui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.stage.Stage;
import logic.Starter;
import logic.simulation.SimulationEngine;

import java.util.ArrayList;
import java.util.List;

public class MapControls {
    Stage map;
    SimulationEngine simulationEngine = null;
    Thread thread;
    @FXML
    private GridPane grid;
    @FXML
    private VBox animalStatistics;
    @FXML
    private VBox allStatistics;


    private SimulationEngine simulationEngine;


    public void run(Starter s){
        grid.getChildren().clear();
        double size = Math.min(700/s.getWidth(),700/s.getHeight());
        for(int i=0;i<s.getHeight();i++){
            grid.getColumnConstraints().add(new ColumnConstraints(size,size,size));
        }
        for(int i=0;i<s.getWidth();i++){
            grid.getRowConstraints().add(new RowConstraints(size,size,size));
//            System.out.println(grid.getRowConstraints().get(0));
        }
        List<Label> list= new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        list.add(s6);
        list.add(s7);
        list.add(s8);
        try{

            simulationEngine = new SimulationEngine(s,grid, allStatistics, animalStatistics);
            this.simulationEngine = simulationEngine;
            Thread thread = new Thread(simulationEngine);

            thread.start();

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(simulationEngine.getMap().getHeight());
            System.out.println(simulationEngine.getMap().getWidth());
        }

    }
    public void setStage(Stage s){
        this.map = s;
    }
    public void pause(ActionEvent e){
        if(this.simulationEngine.paused){
            simulationEngine.resume();
        }else {
            simulationEngine.pause();
        }
    }
    public void stop(ActionEvent e){
        System.out.println("dasdasd");
        thread.stop();
    }
    public void startTracking(ActionEvent e){
        if(!this.simulationEngine.paused) return;

    }
    public void mostPopular(ActionEvent e){

    }
}
