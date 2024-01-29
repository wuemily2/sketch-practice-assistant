package sketch_practice.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sketch_practice.controller.CyclerController;
import javafx.fxml.FXML;
import sketch_practice.util.CyclerCommand;

public class CyclingGUIFXMLController {
    CyclerController cc;
    //Image currentImage;

    //FXML
    @FXML
    ImageView imageDisplay;
    @FXML
    Label timer;// Used to display the current countdown
    @FXML
    Button playToggle;

    public CyclingGUIFXMLController(CyclerController cc){
        this.cc = cc;
    }
    public void setPlayButtonDisplay(String displayText){
        if(this.playToggle != null){
            this.playToggle.setText(displayText);
        }
    }

    public void setImageToDisplay(Image image){
        if(imageDisplay != null){
            this.imageDisplay.setImage(image);
        }
    }

    public void setTimerDisplay(String displayText){
        if(this.timer != null){
            this.timer.setText(displayText);
        }
    }

    @FXML
    public void initialize(){}
    @FXML
    public void onPreviousButtonPressed(){
        cc.executeCyclerCommand(CyclerCommand.GO_BACK);
    }

    @FXML
    public void onPauseOrPlayPressed(){
        cc.executeCyclerCommand(CyclerCommand.TOGGLE_PAUSE_OR_PLAY);
    }

    @FXML
    public void onNextButtonPressed(){
        cc.executeCyclerCommand(CyclerCommand.ADVANCE_NEXT);
    }

    @FXML
    public void toggleImageVisibility(){
        imageDisplay.setVisible(!imageDisplay.isVisible());
    }

}
