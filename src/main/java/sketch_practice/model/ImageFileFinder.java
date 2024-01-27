package sketch_practice.model;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import sketch_practice.util.ImageConstants;

/*
This class stores a list of directories and loose files, and returns a list of file images contained
under the directories, combined with list of loose files that are images.
 */
public class ImageFileFinder extends Observable {
    private HashSet<File> imageDirectories = new HashSet<>();
    private HashSet<File> looseImages = new HashSet<>();

    private static int defaultSearchDepth = 3;
    public boolean addImages(File imageOrDirectory){
        if(imageOrDirectory.isFile() && ImageConstants.javaFXImageFileFilter.accept(imageOrDirectory)){
            this.looseImages.add(imageOrDirectory);
            this.notifyObservers(); // Notify observers that object contents have changed
            return true;
        }else if (imageOrDirectory.isDirectory()){
            this.imageDirectories.add(imageOrDirectory);
            this.notifyObservers();// Notify observers that the object contents have changed.
            return true;
        }
        return false; // return false if adding has failed in any way
    }
    public void setDefaultSearchDepth(int depth){
        if (depth > 0){
            defaultSearchDepth = depth;
        }
    }

    public HashSet<File> getDirectories(){
        return this.imageDirectories;
    }

    public HashSet<File> getLooseFiles(){
        return this.looseImages;
    }

    public static HashSet<File> getImageFilesUpToDepth(File directory){
        //wrapper to call a depth search with a default value
        return getImageFilesUpToDepth(directory, defaultSearchDepth);
    }
    public static HashSet<File> getImageFilesUpToDepth(File directory, int depth){
        //Search for image files up to a certain depth. Don't go into any deeper directories at depth 0.
        HashSet<File> imageFiles = new HashSet<File>();
        if(!directory.isDirectory()){ //Shouldn't happen.
            return new HashSet<>();
        }

        File [] foundImages = directory.listFiles(ImageConstants.javaFXImageFileFilter);
        if(foundImages != null){
            imageFiles.addAll(List.of(foundImages));
        }

        if (depth > 0){ // Search one more layer of directories
            File [] foundDirectories = directory.listFiles(pathname -> pathname.isDirectory());

            if(foundDirectories != null){
                for(File nestedDirectory: foundDirectories){
                    imageFiles.addAll(getImageFilesUpToDepth(nestedDirectory, depth - 1));
                }
            }
        }

        return imageFiles;
    }
    public static HashSet<File> getImageFilesUpToDepth(HashSet<File> directories){
        //wrapper to call a depth search with a default value
        return getImageFilesUpToDepth(directories, defaultSearchDepth);
    }
    public static HashSet<File> getImageFilesUpToDepth(HashSet<File> directories, int depth){
        //Search for image files up to a certain depth. Don't go into any deeper directories at depth 0.
        HashSet<File> imageFiles = new HashSet<File>();
        for(File directory : directories){
            imageFiles.addAll(getImageFilesUpToDepth(directory, depth));
        }
        return imageFiles;
    }

    //Sort through all the image files and return the necessary list.
    // May want to save a memo if, for some reason, this has to be done multiple times and takes too long
    public HashSet<File> getAllImageFiles(){
        //TODO: implement
        HashSet<File> allImageFiles = new HashSet<>();
        allImageFiles.addAll(this.looseImages);
        allImageFiles.addAll(getImageFilesUpToDepth(this.imageDirectories));
        return allImageFiles; // need to delete later.
    }

    public static void main(String[] args){

    }

}
