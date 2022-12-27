package logic.gui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import logic.Parser;
import logic.Starter;
import logic.enums.AnimalBehaviour;
import logic.enums.MapType;
import logic.enums.MutationType;
import logic.enums.PlantType;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class AppControls {
    private int maxSim = 5;
    private int currSim = 0;
    private App app;
    @FXML
    private ChoiceBox<AnimalBehaviour> animal;

    @FXML
    private Spinner<Integer> energyToChild;

    @FXML
    private Spinner<Integer> energyToReproduce;

    @FXML
    private Spinner<Integer> genomeLength;

    @FXML
    private Spinner<Integer> height;

    @FXML
    private ChoiceBox<MapType> map;

    @FXML
    private Spinner<Integer> maximumMutation;

    @FXML
    private Spinner<Integer> minimumMutation;

    @FXML
    private ChoiceBox<MutationType> mutation;

    @FXML
    private Spinner<Integer> newPlants;

    @FXML
    private ChoiceBox<PlantType> plant;

    @FXML
    private Spinner<Integer> plantEnergy;

    @FXML
    private Spinner<Integer> plantNumber;



    @FXML
    private TextField source;

    @FXML
    private Spinner<Integer> startAnimalEnergy;

    @FXML
    private Spinner<Integer> startAnimalNumber;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Spinner<Integer> width;
    Window main;
    FileChooser chooser = new FileChooser();
    File choosen;
    @FXML
    public void initialize() {
        animal.getItems().add(AnimalBehaviour.FULL_PREDISTINATION);
        animal.getItems().add(AnimalBehaviour.A_LITTLE_BIT_OF_MADNESS);
        map.getItems().add(MapType.GLOBE);
        map.getItems().add(MapType.HELL_GATE);
        mutation.getItems().add(MutationType.LIGHT_ADJUSTMENT);
        mutation.getItems().add(MutationType.RANDOMNESS);
        plant.getItems().add(PlantType.EQUATOR);
        plant.getItems().add(PlantType.TOXIC_CORPSES);
        SpinnerValueFactory<Integer> factory;
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,300);
        height.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,300);
        width.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,300);
        energyToChild.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,300); //TODO: Ustawić w zależności od energy to child
        energyToReproduce.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        genomeLength.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10); //TODO: jak wyżej
        minimumMutation.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10); //TODO: jak wyżej
        maximumMutation.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,300);
        newPlants.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,300);
        plantEnergy.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,300);
        plantNumber.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,300);
        startAnimalEnergy.setValueFactory(factory);
        factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,300);
        startAnimalNumber.setValueFactory(factory);


    }

    public void setApp(App app) {
        this.app = app;
    }

    public void userStart(ActionEvent actionEvent) throws IOException {
        if(maxSim==currSim || animal.getValue()==null || map.getValue()==null || mutation.getValue()==null || plant.getValue() == null){
            return;
        }

        Starter s = new Starter(
                height.getValue(),
                width.getValue(),
                map.getValue(),
                plantNumber.getValue(),
                plantEnergy.getValue(),
                newPlants.getValue(),
                plant.getValue(),
                startAnimalNumber.getValue(),
                startAnimalEnergy.getValue(),
                energyToReproduce.getValue(),
                energyToChild.getValue(),
                minimumMutation.getValue(),
                maximumMutation.getValue(),
                mutation.getValue(),
                genomeLength.getValue(),
                animal.getValue()
        );
        newWindow(s);
    }
    public void fileStart(ActionEvent actionEvent) throws IOException, IllegalAccessException {
        if(maxSim==currSim || choosen==null){
            return;
        }
        newWindow(Parser.fromFile(choosen));

    }
    public void fileChoose(ActionEvent actionEvent){
        main = app.mainStage;
        choosen = chooser.showOpenDialog(main);
        source.setText(choosen.getPath());
    }
    private void newWindow(Starter s) throws IOException {
//        currSim+=1;
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(new File("src/main/java/logic/gui/main/map.fxml").toURI().toURL());
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        MapControls controls = loader.getController();
        stage.setOnCloseRequest(event -> {controls.stop(null);});
        controls.setStage(stage);
        controls.run(s);
        stage.setScene(scene);
        stage.show();
    }
}
