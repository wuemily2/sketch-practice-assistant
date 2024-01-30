module sketch_practice{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens sketch_practice.view to javafx.fxml;
    exports sketch_practice;
}