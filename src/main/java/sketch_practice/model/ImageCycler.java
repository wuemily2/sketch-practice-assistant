package sketch_practice.model;

import sketch_practice.util.Observable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ImageCycler extends Observable {

    private ArrayList<File> imageFiles; // assume received list of image files is exactly images.
    private int currentImage; // track index of the current image
    private boolean visible = false;

    // Take in a list of imagefiles to cycle through
    public ImageCycler(ArrayList<File> imageFiles){
        this.imageFiles = imageFiles;
        if (imageFiles != null && !imageFiles.isEmpty()){
            Collections.shuffle(this.imageFiles);
            this.currentImage = 0;
        }
    }

    public void toggleVisibility() {
        this.visible = !this.visible;
        this.notifyObservers();
    }

    // Assumes that a validindex is received. changes the image to the new index, and
    // notify the observers that current image has changed.
    private void changeImage(int validIndex){
        this.currentImage = validIndex;
        this.notifyObservers(); // Notify observers that image has changed
    }

    //TODO: should we add file error checking here in case the images don't exist anymore?
    // Trying to remove potentially multiple offending files could be work intensive for an arraylist
    // but the same could be said for skipping over multiple offending files.
    public void go_to_next_image(){
        int next_index = this.currentImage + 1;
        if(next_index < this.imageFiles.size()){
            this.changeImage(next_index);
        }
        // Do nothing if index goes out of bounds
    }

    public void go_to_previous_image(){
        int previous_index = this.currentImage - 1;
        if(previous_index >= 0){
            this.changeImage(previous_index);
        }
    }

    public void go_to_image_at_index(int index){
        if(index < this.imageFiles.size() && index >= 0){
            this.changeImage(index);
        }
    }

    public int getCurrentImageIndex(){
        return this.currentImage;
    }

    public File get_current_image(){
        return this.imageFiles.get(this.currentImage); // TODO: may need to add out of bounds error checking?
    }

}
