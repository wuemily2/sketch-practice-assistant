module sketch_practice{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens sketch_practice.view to javafx.fxml;
    exports sketch_practice;
    exports sketch_practice.model;
    exports sketch_practice.view;
    exports sketch_practice.controller;
    exports sketch_practice.util;
}