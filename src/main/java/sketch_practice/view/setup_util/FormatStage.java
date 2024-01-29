package sketch_practice.view.setup_util;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FormatStage {
    static final double SCREEN_PRESENCE_PERCENTAGE = 0.8;
    public static void formatStage(Stage stage){
        //Acquire the main screen
        Screen screen = Screen.getPrimary();
        //Get usable bounds of the main screen
        Rectangle2D bounds = screen.getVisualBounds(); // Visual bounds account for things like task bar area
        //Values used for centering the stage and taking up 0.8 of the width and height
        double offsetPercentage = ( 1 - SCREEN_PRESENCE_PERCENTAGE) / 2;
        //Set X Values
        double screenWidth = bounds.getWidth();
        double xOffset = screenWidth * offsetPercentage;
        stage.setWidth(screenWidth * SCREEN_PRESENCE_PERCENTAGE);
        stage.setX(xOffset);
        //Set Y values
        double screenHeight = bounds.getHeight();
        double yOffset = screenHeight * offsetPercentage;
        stage.setHeight(screenHeight * SCREEN_PRESENCE_PERCENTAGE);
        stage.setY(yOffset);
    }
}
