package sketch_practice.model;

import sketch_practice.util.Observable;

import java.util.ArrayList;

public class ImageCycler extends Observable {

    private ArrayList<String> imageDirectories;
    //private Timer imageCountdown = new Timer(); // Not sure which Timer class to use

    // Take in a list of imageDirectories to cycle through
    public ImageCycler(ArrayList<String> imageDirectories){
        this.imageDirectories = imageDirectories;
    }

    public void setUpNewImages(ArrayList<String> imageDirectories){

    }

}
