package sketch_practice.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;

/*
This class stores a list of directories and loose files, and returns a list of file images contained
under the directories, combined with list of loose files that are images.
 */
public class ImageFileFinder extends Observable {
    private HashSet<File> imageDirectories = new HashSet<>();
    private HashSet<File> looseImages = new HashSet<>();
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

    public HashSet<File> getDirectories(){
        return this.imageDirectories;
    }

    public HashSet<File> getLooseFiles(){
        return this.looseImages;
    }

    //Sort through all the image files and return the necessary list.
    // May want to save a memo if, for some reason, this has to be done multiple times and takes too long
    public HashSet<File> getAllImageFiles(){
        //TODO: implement
        return new HashSet<File>(); // need to delete later.
    }

}
