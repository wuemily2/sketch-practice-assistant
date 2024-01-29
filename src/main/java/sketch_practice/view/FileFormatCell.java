package sketch_practice.view;

import javafx.scene.control.ListCell;

import java.io.File;

public class FileFormatCell extends ListCell<File> {
    @Override protected void updateItem(File item, boolean empty){
        super.updateItem(item, empty);

        setText("");
        if (item != null){
            String indicator;
            if(item.isDirectory()){
                indicator = "(Dir)";
            }else{
                indicator = "(File)";
            }
            setText(String.format("%s %s", indicator, item.getName()));
        }
    }
}
