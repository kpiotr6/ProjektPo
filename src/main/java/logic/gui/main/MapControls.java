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
import logic.CSVWritter;
import logic.Starter;
import logic.simulation.SimulationEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapControls {
    Stage map;
    SimulationEngine simulationEngine = null;
    Thread thread;
    CSVWritter writter = new CSVWritter();
    @FXML
    private GridPane grid;
    @FXML
    private VBox animalStatistics;
    @FXML
    private VBox allStatistics;




    public void run(Starter s) throws IOException {
        writter.open();
        grid.getChildren().clear();
        double size = Math.min(700/s.getWidth(),700/s.getHeight());
        for(int i=0;i<s.getWidth();i++){
            grid.getColumnConstraints().add(new ColumnConstraints(size,size,size));
        }
        for(int i=0;i<s.getHeight();i++){
            grid.getRowConstraints().add(new RowConstraints(size,size,size));
        }

        try{

            simulationEngine = new SimulationEngine(s,grid, allStatistics, animalStatistics,writter);
            thread = new Thread(simulationEngine);
            thread.start();

        }
        catch (Exception e){
            e.printStackTrace();
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
        simulationEngine.stop();
        map.close();
    }
    public void startTracking(ActionEvent e){
        this.simulationEngine.tracking = false;
        this.simulationEngine.eraseAnimalStatistic();
    }
}
