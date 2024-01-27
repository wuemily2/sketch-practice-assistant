package sketch_practice.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;

/*
This class stores a list of directories and loose files, and returns a list of file images contained
under the directories, combined with list of loose files that are images.
 */
public class ImageFileFinder extends Observable {
    private ArrayList<File> imageDirectories = new ArrayList<>();
    private ArrayList<File> looseImages = new ArrayList<>();

    public boolean addImages(File imageOrDirectory){
        if(imageOrDirectory.isFile()){
            //TODO: Add error checking for valid image file
            this.looseImages.add(imageOrDirectory);
            this.notifyObservers(); // Notify observers that object contents have changed
            return true;
        }else if (imageOrDirectory.isDirectory()){
            this.imageDirectories.add(imageOrDirectory);
            this.notifyObservers();// Notify observers that the object contents have changed.
            return true;
        } else {// Not sure if there is a case where it isn't a directory or file
            return false;
        }
    }

    public ArrayList<String> getDirectoriesAsString(){
        return new ArrayList<String>(); //TODO implement. Return a string representation of the directories
    }

    public ArrayList<String> getLooseFilesAsString(){
        return new ArrayList<String>(); //TODO implement. Return a string representation of the loose files.
    }

    //Sort through all the image files and return the necessary list.
    // May want to save a memo if, for some reason, this has to be done multiple times and takes too long
    public ArrayList<File> getAllImageFiles(){
        //TODO: implement
        return looseImages; // need to delete later.
    }

}
