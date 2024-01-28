package sketch_practice.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sketch_practice.controller.CyclerController;
import sketch_practice.model.CountdownTimer;
import sketch_practice.util.Observable;
import sketch_practice.util.Observer;
import javafx.stage.Stage;



public class SketchPracticeGUI implements Observer {
    private Stage stage;
    private Scene masterScene;

    private SettingsGUI settingsGUI;
    private CyclingGUI cyclingGUI;

    private CyclerController controller;

    //
    public Label timeLabel = new Label("TimeLabel");
    public Label userMessageLabel = new Label("Hello!");

    class SettingsGUI extends Pane {

        private EventHandler<ActionEvent> removal_handler;
        private Button removalButton = new Button("Remove directory.");
        public SettingsGUI (){ // build self

            // TODO Remove test
            Button switchbutton = new Button("SettingsGui");
            switchbutton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    SketchPracticeGUI.this.switchView();
                }
            });
            this.getChildren().add(switchbutton);
            //

            // Make children first and then put into node list of VBOX

            //Create VBOX
            //VBox settingsList = new VBox();

            //this.getChildren().add(settingsList);
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
