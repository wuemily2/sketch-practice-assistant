package sketch_practice.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sketch_practice.controller.CyclerController;
import sketch_practice.model.CountdownTimer;
import sketch_practice.model.ImageCycler;
import sketch_practice.model.ImageFileFinder;
import sketch_practice.util.CyclerCommand;
import sketch_practice.util.Observable;
import sketch_practice.util.Observer;

import java.io.File;
import java.io.FileInputStream;
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
            Label userMessage = new Label("Select Images to rotate through + session time, and then press start!");
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
            VBox rightBar = new VBox(userMessage, startButton);
            rightBar.setSpacing(20);
            rightBar.setAlignment(Pos.TOP_LEFT);

            //Create VBOX
            HBox settingsList = new HBox(settingsGUIComponent, rightBar);
            settingsList.prefHeightProperty().bind(this.heightProperty()); //ensure it resizes with parent
            settingsList.prefWidthProperty().bind(this.widthProperty()); //ensure it resizes with parent
            settingsGUIComponent.prefHeightProperty().bind(settingsList.heightProperty());
            settingsGUIComponent.prefWidthProperty().bind(settingsList.widthProperty());

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
            Label errLabel = new Label("    Don't see an image? If so, try toggling or changing the image.");
            HBox topBar = new HBox(backButton, errLabel);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CyclingGUI.fxml"));
            this.cyclingController = new CyclingGUIFXMLController(controller);
            fxmlLoader.setController(cyclingController);
            VBox settingsGUIComponent = fxmlLoader.load();
            settingsGUIComponent.setSpacing(5);

            VBox cyclingLayout = new VBox(topBar, settingsGUIComponent);
            cyclingLayout.setSpacing(5);
            this.getChildren().add(cyclingLayout);
            cyclingLayout.prefHeightProperty().bind(this.heightProperty());
            cyclingLayout.prefWidthProperty().bind(this.widthProperty());
            settingsGUIComponent.prefHeightProperty().bind(cyclingLayout.prefHeightProperty());
            settingsGUIComponent.prefWidthProperty().bind(cyclingLayout.prefWidthProperty());
        }
    }

    // Take in a stage created by the application on the JavaFX thread; Make sure to instantiate on same thread
    public SketchPracticeGUI(Stage applicationStage, CyclerController controller) throws IOException {
        this.controller = controller;
        this.stage = applicationStage;
        //
        this.settingsGUI = new SettingsGUI();
        this.cyclingGUI = new CyclingGUI();
        //

        //bind height and width
        settingsGUI.prefHeightProperty().bind(this.stage.heightProperty());
        settingsGUI.prefWidthProperty().bind(this.stage.widthProperty());
        cyclingGUI.prefHeightProperty().bind(this.stage.heightProperty());
        cyclingGUI.prefWidthProperty().bind(this.stage.widthProperty());

        //Set Scene and style sheets applied to the scene
        this.masterScene = new Scene(settingsGUI); // Initially start the app with a settings GUI
        this.masterScene.getStylesheets().add(String.valueOf(getClass().getResource("simpleStyleSheet.css")));

        //
        this.stage.setScene(this.masterScene);
    }

    public void switchView(){ // Switch between the scenes.
        this.masterScene.setRoot(
                this.masterScene.getRoot() == settingsGUI ? cyclingGUI : settingsGUI);
    }

    public void showStage(){
        this.stage.show();
    }

    public void update(Observable o) {
        if(o instanceof CountdownTimer){
            CountdownTimer timer = (CountdownTimer) o;
            boolean isPlaying = timer.getIfTimerIsRunning();
            if(isPlaying){
                this.cyclingGUI.cyclingController.setPlayButtonDisplay("Pause");
            }else{
                this.cyclingGUI.cyclingController.setPlayButtonDisplay("Play");
            }
            this.cyclingGUI.cyclingController.setTimerDisplay(timer.getTimeLeftAsString());
        }else if(o instanceof ImageFileFinder){
            ImageFileFinder finder = (ImageFileFinder) o;
            this.settingsGUI.settingsController.modifyFileObjectEntries(finder.getCurrentSelection());
        }else if(o instanceof ImageCycler){
            ImageCycler cycler = (ImageCycler) o;
            File newImage = cycler.get_current_image();
            try{
                // TODO - check if our file format checks are robust enough
                FileInputStream imageInput = new FileInputStream(newImage);
                Image image = new Image(imageInput);
                this.cyclingGUI.cyclingController.setImageToDisplay(image);
                imageInput.close(); // Need to explicitly call close on input streams to save system resources.
            }catch (Exception e){//For FileNotFound as well as any unforeseen exceptions
                // Just erase the image. for now. We'll add supporting text later.
                this.cyclingGUI.cyclingController.setImageToDisplay(null);
            }
        }

    }
}
