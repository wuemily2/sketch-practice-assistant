package sketch_practice.model;

import java.io.File;
import java.util.ArrayList;

/*
This class stores a list of directories and loose files, and returns a list of file images contained
under the directories, combined with list of loose files that are images.
 */
public class ImageFileFinder {
    private ArrayList<File> imageDirectories = new ArrayList<>();
    private ArrayList<File> looseImages = new ArrayList<>();

    public boolean addImages(File imageOrDirectory){
        if(imageOrDirectory.isFile()){
            //TODO: Add error checking for valid image file
            this.looseImages.add(imageOrDirectory);
            return true;
        }else if (imageOrDirectory.isDirectory()){
            this.imageDirectories.add(imageOrDirectory);
            return true;
        } else {// Not sure if there is a case where it isn't a directory or file
            return false;
        }
    }

    //Sort through all the image files and return the necessary list.
    public ArrayList<File> getAllImageFiles(){
        //TODO: implement
        return looseImages; // need to delete later.
    }

}
