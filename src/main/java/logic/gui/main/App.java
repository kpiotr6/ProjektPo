package logic.gui.main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.enums.MapDirection;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class App extends Application {
    Scene scene;
    AnchorPane root;
    FXMLLoader loader;
    Stage mainStage;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(scene);
        primaryStage.show();
        mainStage = primaryStage;
    }

    @Override
    public void init() throws Exception {

            super.init();
            loader = new FXMLLoader(new File("src/main/java/logic/gui/main/main.fxml").toURI().toURL());
            root = loader.load();
            scene = new Scene(root);
            AppControls appControls = loader.getController();
            appControls.setApp(this);
//        ObservableList<Node> list= root.getChildren();
//        list.

    }

}
