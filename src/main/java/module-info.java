module sketch_practice{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    //opens sketch_practice to javafx.fxml;
    exports sketch_practice;
    exports sketch_practice.model;
}