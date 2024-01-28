package sketch_practice.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sketch_practice.controller.CyclerController;

import java.io.File;

public class RemoveFileObjectEventHandler implements EventHandler<ActionEvent> {
    CyclerController cc;
    File targetFile;
    public RemoveFileObjectEventHandler(CyclerController cc, File targetFile){
        this.cc = cc;
        this.targetFile = targetFile;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        this.cc.removeFileObject(this.targetFile);
    }
}
