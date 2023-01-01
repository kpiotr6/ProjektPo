package logic;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import logic.gui.main.App;

public class Main {
    public static void main(String[] args){
        try{
            Application.launch(App.class,args);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR,e.getMessage(), ButtonType.OK);
            alert.showAndWait();

        }
    }
}
