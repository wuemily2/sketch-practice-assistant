package sketch_practice.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sketch_practice.controller.CyclerController;
import sketch_practice.model.CountdownTimer;
import sketch_practice.util.Observable;
import sketch_practice.util.Observer;
import javafx.stage.Stage;

import java.io.File;


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
        ObservableList<File> fileObjectEntries; // Need to expose to update later
        public SettingsGUI (){ // build self
            // Make children first and then put into node list of VBOX
            //TODO: separate components into individual files for readability

            //================== Setup for adding images ===============
            //Label for indicating that you can add files
            Label label = new Label("Add Images:");

            //Add a button for Adding a Directory
            Button addDirectoryButton = new Button("Add Dir");
            Tooltip addDirImagesTooltip = new Tooltip(
                    "Select directories to add images from, up to a specified depth.");
            addDirectoryButton.setTooltip(addDirImagesTooltip);
            addDirectoryButton.setOnAction(new AddDirectoryEventHandler(controller, stage));

            //Add a button for adding loose image files
            Button addFilesButton = new Button("Add individual images");
            addFilesButton.setOnAction(new AddFilesEventHandler(controller, stage));

            // Add a selection for removal
            this.fileObjectEntries = FXCollections.observableArrayList();
            ListView<File> fileList = new ListView<>(this.fileObjectEntries);
            //Add a button for removing a file based on selection
            Button removeFileButton = new Button("Remove Selected");
            removeFileButton.setOnAction(actionEvent -> {
                File selectedFile = fileList.getSelectionModel().getSelectedItem();
                if (selectedFile != null) {
                    SketchPracticeGUI.this.controller.removeFileObject(selectedFile);
                }
            });

            // ================= Max Depth Input =======================
            //TextField Section for setting MaxDepth
            Tooltip depthTip = new Tooltip(
                    "Enter a number to represent the maximum depth to search for image files.");
            Label depthLabel = new Label("Max Depth:");
            depthLabel.setTooltip(depthTip);
            TextField depthField = new TextField("0");
            depthField.setTooltip(depthTip);
            depthField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    depthField.setText(newValue.replaceAll("[\\D]", ""));
                }
            });

            //================== Time Input ==========================
            // Radio Button Selection
            //TODO:
            TimeSelectionRadioButtonGroup radioList = new TimeSelectionRadioButtonGroup();

            // -> Custom input for time
            Label customTime = new Label("Custom Time:");
            TextField timeField = new TextField("Time (in seconds)");
            Tooltip timeSettingTooltip = new Tooltip("Type the desired time here in seconds");
            timeField.textProperty().addListener((observable, oldValue, newValue) -> {
                radioList.selectCustomToggle(); // Switch to custom selection
                if (!newValue.matches("\\d*")) {
                    depthField.setText(newValue.replaceAll("[\\D]", ""));
                }
            });
            //

            // Add a button to start cycling through images
            Button startButton = new Button("Start!");
            startButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    SketchPracticeGUI.this.switchView();
                }
            });
            //this.getChildren().add(startButton);

            //Create VBOX
            VBox settingsList = new VBox(radioList, startButton); // TODO add rest of the node list here

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
    public SketchPracticeGUI(Stage applicationStage, CyclerController controller){
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
            timeLabel.setText(timer.getTimeLeftAsString());
        }

    }
}
