package sketch_practice;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sketch_practice.controller.CyclerController;
import sketch_practice.model.CountdownTimer;
import sketch_practice.model.ImageFileFinder;
import sketch_practice.model.JavaFXCountdownTimer;
import sketch_practice.view.SketchPracticeGUI;
import sketch_practice.view.setup_util.FormatStage;

import java.io.IOException;
import java.io.InputStream;

public class SketchPractice extends Application {
    public static void main(String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Give the Application a name in the top bar
        stage.setTitle("Sketch Practice Assistant");
        //Ensure stage closes properly
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();//Terminate JavaFX Application Thread
            System.exit(0); // Close Java process
        });
        //Set default stage size using setup file
        FormatStage.formatStage(stage);

        //Create model components
        ImageFileFinder fileFinder = new ImageFileFinder();
        CountdownTimer timer = new JavaFXCountdownTimer();
        // Setup controller
        CyclerController controller = new CyclerController(fileFinder, timer);
        //Setup stage in View object, with controller to setup event handlers
        SketchPracticeGUI view = new SketchPracticeGUI(stage, controller);
        //Attach observers to observables
        fileFinder.attach(view);
        timer.attach(view);
        //Set a stage icon
        try{
            InputStream iconStream = getClass().getResourceAsStream("app_icon.png");
            assert iconStream != null;
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
            iconStream.close();
        }catch (Exception e){
            System.err.println(e.getMessage());
            System.err.println("Could not get an icon");
        }

        //Finally, show the current scene.
        view.showStage();

    }
}
