package sketch_practice.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import sketch_practice.util.Observable;
import sketch_practice.util.Observer;
import javafx.stage.Stage;

import java.io.File;

public class SketchPracticeGUI extends Application implements Observer {
    private Stage stage;
    public void switchView(){

    }
    public Stage getStage(){
        return this.stage;
    }

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
            DirectoryChooser directoryChooser = new DirectoryChooser(); //May need to use Jfilechooser
            File selectedDirectory = directoryChooser.showDialog(newWindow);
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

    public void update(Observable o){

    }
}
