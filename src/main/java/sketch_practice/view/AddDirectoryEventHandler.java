package sketch_practice.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sketch_practice.controller.CyclerController;

import java.io.File;

public class AddDirectoryEventHandler implements EventHandler<ActionEvent> {
    private final CyclerController cc;
    private final Stage stage; // The stage we want to freeze for opening a directory choosing dialog.
    public AddDirectoryEventHandler(CyclerController cc, Stage stage){
        this.cc = cc;
        this.stage = stage;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser(); //May need to use Jfilechooser
        directoryChooser.setTitle("Select a directory to add images from");
        File selectedDirectory = directoryChooser.showDialog(this.stage);
        if (selectedDirectory != null) {
            this.cc.addFileObject(selectedDirectory);
        }
    }
}
