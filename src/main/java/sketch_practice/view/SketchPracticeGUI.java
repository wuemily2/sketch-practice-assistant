package sketch_practice.view;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sketch_practice.controller.CyclerController;
import sketch_practice.model.CountdownTimer;
import sketch_practice.model.ImageCycler;
import sketch_practice.model.ImageFileFinder;
import sketch_practice.util.CyclerCommand;
import sketch_practice.util.Observable;
import sketch_practice.util.Observer;
import javafx.stage.Stage;

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
                    // Set up recursion depth
                    int depth = settingsController.getDepth();
                    int seconds = settingsController.getSelectedTime();
                    if(depth >= 0 && seconds > 0){
                        SketchPracticeGUI.this.controller.setImageFileFinderSearchDepth(depth);
                        SketchPracticeGUI.this.controller.setCountDownTimer(seconds);
                    }else{
                        userMessage.setText("Invalid depth or fewer than 1 seconds inputted!");
                        return;
                    }
                    if(SketchPracticeGUI.this.controller.startSession(SketchPracticeGUI.this)){
                        SketchPracticeGUI.this.switchView(); //TODO: change if needed
                    }else{
                        userMessage.setText(
                                "Can't start session for some reason!\n Did you add any images?"
                        );//Shouldn't reach here unless you add 0 images.
                    }

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
        CyclingGUIFXMLController cyclingController;
        public CyclingGUI () throws IOException {
            Button backButton = new Button("<- Go Back");
            backButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    SketchPracticeGUI.this.controller.executeCyclerCommand(CyclerCommand.EXIT_CYCLING);
                    SketchPracticeGUI.this.switchView();
                }
            });
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CyclingGUI.fxml"));
            this.cyclingController = new CyclingGUIFXMLController(controller);
            fxmlLoader.setController(cyclingController);
            VBox settingsGUIComponent = fxmlLoader.load();

            VBox cyclingLayout = new VBox(backButton, settingsGUIComponent);
            this.getChildren().add(cyclingLayout);
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

    public void update(Observable o) {
        if(o instanceof CountdownTimer){
            CountdownTimer timer = (CountdownTimer) o;
            boolean isPlaying = timer.getIfTimerisRunning();
            if(isPlaying){
                this.cyclingGUI.cyclingController.setPlayButtonDisplay("Pause");
            }else{
                this.cyclingGUI.cyclingController.setPlayButtonDisplay("Play");
            }
            this.cyclingGUI.cyclingController.setTimerDisplay(timer.getTimeLeftAsString());
        }else if(o instanceof ImageFileFinder){
            ImageFileFinder finder = (ImageFileFinder) o;
            this.settingsGUI.settingsController.modifyFileObjectEntries(finder.getCurrentSelection());
        }else if(o instanceof ImageCycler){//TODO add more error checking
            ImageCycler cycler = (ImageCycler) o;
            Image newImage = cycler.get_current_image();
            if(newImage == null) {
                this.controller.executeCyclerCommand(CyclerCommand.ADVANCE_NEXT);//TODO: FIX - This won't work if we wanna go backwards.
            }else{
                this.cyclingGUI.cyclingController.setImageToDisplay(newImage);
            }

        }

    }
}
