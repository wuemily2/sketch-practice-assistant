package sketch_practice.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sketch_practice.controller.CyclerController;
import sketch_practice.util.ImageConstants;
import sketch_practice.util.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//For reference
//https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method
//For initializing a listener on Textfield
//https://coderanch.com/t/697727/java/Action-routine-onInputMethodTextChanged-called
public class SettingsGUIFXMLController {
    //Personal Variables
    CyclerController cc; //Must reference controller being used and watched by the view
    private boolean initialized = false;
    //FXML variables

    //Item List for selected files
    ObservableList<File> fileObjectEntries = FXCollections.observableArrayList();
    @FXML
    ComboBox<File> fileList;

    //Depth Text Field
    @FXML
    TextField customDepthField;

    //Time Radio List
    @FXML
    TimeSelectionRadioButtonGroup radioGroup;

    //Custom Time Field
    @FXML
    TextField customTimeField;
    public SettingsGUIFXMLController(CyclerController cc){
        this.cc = cc;
    }

    //Intended to be called when updating based on observing the Imagefilefinder,
    //When done so, refresh the list based on the hashset in Imagefilefinder.
    public void modifyFileObjectEntries(ArrayList<File> newFiles){// remove everything from list and add new
        this.fileObjectEntries.removeAll();
        this.fileObjectEntries.addAll(newFiles);
    }

    @FXML
    public void initialize(){
        //Initialize the list for ListView
        assert fileList != null : "Could not find the ListView for files with fx-id: fileList";
        fileList = new ComboBox<>(fileObjectEntries);

        assert customDepthField != null : "Could not find customDepthField";
        customDepthField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                customDepthField.setText(newValue.replaceAll("[\\D]", ""));
            }
        });

        //Add a listener to the custom time field
        assert customTimeField != null : "Could not find customTimeField";
        customTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
            radioGroup.selectCustomToggle();
            if (!newValue.matches("\\d*")) {
                customTimeField.setText(newValue.replaceAll("[\\D]", ""));
            }
        });

        // Placing this here for now
        initialized = true;
    }

    @FXML
    public void onAddDirButtonClicked(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a directory to add images from");
        File selectedDirectory = directoryChooser.showDialog(((Node)event.getSource()).getScene().getWindow());
        if (selectedDirectory != null) {
            this.cc.addFileObject(selectedDirectory);
        }
    }

    @FXML
    public void onAddFilesButtonClicked(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select image files");
        //Since we only want to select images, let's make a filter here
        FileChooser.ExtensionFilter imageExtensionFilter =
                new FileChooser.ExtensionFilter("Images", ImageConstants.getAggregateEndingsList()); //TODO: Fix because this isn't working
        fileChooser.getExtensionFilters().add(imageExtensionFilter);
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(((Node)event.getSource()).getScene().getWindow());
        if (selectedFiles != null) {
            for(File file: selectedFiles){
                this.cc.addFileObject(file);
            }
        }
    }

    @FXML
    public void onRemoveFileButtonClicked(){
        File selectedFile = fileList.getSelectionModel().getSelectedItem();
        if (selectedFile != null) {
            this.cc.removeFileObject(selectedFile);
        }
    }

    public int getDepth(){
        assert initialized : "Calling non fxml method on fx-id components";
        if(!Objects.equals(customDepthField.getText(), "")){
            return Integer.valueOf(customDepthField.getText());
        }
        return -1;
    }

    public int getSelectedTime(){
        assert initialized : "not initialized (2)";
        int selectedTime = radioGroup.getSelectedTime();
        if (selectedTime == -1){
            return getCustomTime();
        }
        return selectedTime;
    }

    public int getCustomTime(){
        assert initialized : "Calling non fxml method on fx-id components";
        if(!Objects.equals(customTimeField.getText(), "")){
            return Integer.valueOf(customTimeField.getText());
        }
        return -1;
    }

}
