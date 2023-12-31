package sketch_practice;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SketchPractice extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Create Stage
        Stage newWindow = stage;
        newWindow.setTitle("New Scene");
        //Create view in Java
        Label title = new Label("This is a pretty simple view!");
        TextField textField = new TextField("Enter your name here");
        Button button = new Button("OK");
        button.setOnAction(event -> {
            //handle button press
        });
        VBox container = new VBox(title, textField, button);
        //Style container
        container.setSpacing(15);
        container.setPadding(new Insets(25));
        container.setAlignment(Pos.CENTER);
        //Set view in window
        newWindow.setScene(new Scene(container));
        //Launch
        newWindow.show();
    }
}
