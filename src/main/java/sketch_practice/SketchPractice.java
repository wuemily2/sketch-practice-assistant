package sketch_practice;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sketch_practice.controller.CyclerController;
import sketch_practice.model.ImageCycler;
import sketch_practice.model.ImageFileFinder;
import sketch_practice.model.JavaFXCountdownTimer;
import sketch_practice.model.CountdownTimer;
import sketch_practice.view.SketchPracticeGUI;

import java.io.IOException;

public class SketchPractice extends Application {
    public static void main(String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Give the Application a name in the top bar
        stage.setTitle("Sketch Practice Assistant");
        //Ensure stage closes properly
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Platform.exit();//Terminate JavaFX Application Thread
                System.exit(0); // Close Java process
            }
        });
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

        //Finally, show the current scene.
        view.showStage();
        /*CountdownTimer timer = new JavaFXCountdownTimer(600);
        timer.attach(view);

        stage.setTitle("New Scene");
        //Create view in Java
        Label title = view.timeLabel;
        TextField textField = new TextField("Enter your name here");
        Button button = new Button("OK");

        VBox container = new VBox(title, textField, button);
        //Style container
        container.setSpacing(15);
        container.setPadding(new Insets(25));
        container.setAlignment(Pos.CENTER);
        *//*button.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser(); //May need to use Jfilechooser
            File selectedDirectory = directoryChooser.showDialog(this.stage);
            if (selectedDirectory != null) {
                title.setText(selectedDirectory.getName());
                File[] FilesList = selectedDirectory.listFiles();
                if (FilesList == null) {
                    return;
                }
                for (File file : FilesList) {
                    System.out.println(file.toString());
                }
            }
        });*//*
        //Set view in window
        stage.setScene(new Scene(container));
        //Launch
        stage.show();
        timer.startTimer();
        System.out.println(timer.getTimeLeftAsString());*/
    }
}
