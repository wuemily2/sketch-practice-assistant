package sketch_practice.view;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class TimeSelectionRadioButtonGroup extends VBox {
    // 30 seconds, 60 seconds, 2 minutes, 5 minutes, 10 minutes, 30 minutes
    // The two lists below have to be the same length
    // We could put it in a hashmap but uh... whatever.
    private ToggleGroup group = new ToggleGroup();
    private RadioButton custom = new RadioButton("Custom");

    public TimeSelectionRadioButtonGroup(){
        super();
        final int [] TIMESELECTION = {30, 60, 120, 300, 600, 1800}; //In seconds
        final String [] TEXTTIMESELECTION = {"30s", "60s", "2mins", "5mins", "10mins", "30mins"};
        //Setup Selection
        for (int i = 0; i < TIMESELECTION.length; i++){
            RadioButton newButton = new RadioButton(TEXTTIMESELECTION[i]);
            newButton.setUserData(TIMESELECTION[i]);
            newButton.setToggleGroup(group);
            this.getChildren().add(newButton);
        }

        this.custom.setUserData(null);
        this.custom.setToggleGroup(group);
        this.getChildren().add(custom);
        // Make sure the first is selected
        this.group.getToggles().get(0).setSelected(true);
    }

    public void selectCustomToggle(){
        this.group.selectToggle(custom);
    }

    public int getSelectedTime(){
        Object data = this.group.getSelectedToggle().getUserData();
        if( data != null){
            return (int) data;
        }
        return -1; // Invalid value.
    }

}
