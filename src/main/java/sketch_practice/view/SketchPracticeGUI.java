package sketch_practice.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sketch_practice.controller.CyclerController;
import sketch_practice.model.CountdownTimer;
import sketch_practice.model.ImageFileFinder;
import sketch_practice.util.Observable;
import sketch_practice.util.Observer;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SketchPracticeGUI implements Observer {
    private Stage stage;
    private Scene masterScene;

    private SettingsGUI settingsGUI;
    private CyclingGUI cyclingGUI;

    private CyclerController controller;

    //
    public Label timeLabel = new Label("TimeLabel");
    public Label userMessageLabel = new Label("Hello!"); // TODO - change this to a popup.

    class SettingsGUI extends Pane {
        //private Button removalButton = new Button("Remove directory.");
        SettingsGUIFXMLController settingsController;
        public SettingsGUI () throws IOException { // build self
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingsGUI.fxml"));
            this.settingsController = new SettingsGUIFXMLController(controller);
            fxmlLoader.setController(settingsController);
            HBox settingsGUIComponent = fxmlLoader.load();

            // Add error message field to go with below button.
            Label userMessage = new Label("Select Images to rotate through \n+ session time, and then press start");
            // Add a button to start cycling through images. Do this outside of the fxml file
            Button startButton = new Button("Start!");
            startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    System.out.println(settingsController.getDepth()); //WORKS!
                    SketchPracticeGUI.this.switchView();
                }
            });
            HBox bottomBar = new HBox(userMessage, startButton);
            //this.getChildren().add(startButton);

            //Create VBOX
            //VBox settingsList = new VBox(radioList, startButton); // TODO add rest of the node list here
            VBox settingsList = new VBox(settingsGUIComponent, bottomBar);

            this.getChildren().add(settingsList);
        }


    }

    class CyclingGUI extends Pane{
        public CyclingGUI (){
            Button switchbutton = new Button("CyclingGui");
            switchbutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    SketchPracticeGUI.this.switchView();
                }
            });
            this.getChildren().add(switchbutton);
        }
    }

    // Take in a stage created by the application on the JavaFX thread; Make sure to instantiate on same thread
    public SketchPracticeGUI(Stage applicationStage, CyclerController controller) throws IOException {
        this.controller = controller;
        this.stage = applicationStage;
        //TODO - implement the following guis
        this.settingsGUI = new SettingsGUI();
        this.cyclingGUI = new CyclingGUI();
        //
        this.masterScene = new Scene(settingsGUI); // Initially start the app with a settings GUI
        this.stage.setScene(this.masterScene);
    }

    public void switchView(){ // Switch between the scenes.
        this.masterScene.setRoot(
                this.masterScene.getRoot() == settingsGUI ? cyclingGUI : settingsGUI);
    }
    public Stage getStage(){
        return this.stage;
    }

    public void showStage(){
        stage.show();
    }

    public void hideStage(){
        stage.hide();
    }

    public void update(Observable o){
        if(o instanceof CountdownTimer){
            CountdownTimer timer = (CountdownTimer) o;
            //TODO: Add code to change on screen timer value
        }else if(o instanceof ImageFileFinder){
            ImageFileFinder finder = (ImageFileFinder) o;
            this.settingsGUI.settingsController.modifyFileObjectEntries(finder.getCurrentSelection());
        }

    }
}
