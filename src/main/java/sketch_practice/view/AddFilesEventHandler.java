package sketch_practice.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sketch_practice.controller.CyclerController;
import sketch_practice.util.ImageConstants;

import java.io.File;
import java.util.List;

public class AddFilesEventHandler implements EventHandler<ActionEvent> {
    CyclerController cc;
    Stage stage;
    public AddFilesEventHandler(CyclerController cc, Stage stage){
        this.cc = cc;
        this.stage = stage;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        //Since we only want to select images, let's make a filter here
        FileChooser.ExtensionFilter imageExtensionFilter =
                new FileChooser.ExtensionFilter("Images", ImageConstants.getAggregateEndingsList());
        fileChooser.getExtensionFilters().add(imageExtensionFilter);
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(this.stage);
        if (selectedFiles != null) {
            for(File file: selectedFiles){
                this.cc.addFileObject(file);
            }
        }
    }
}
