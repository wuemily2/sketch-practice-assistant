package sketch_practice.util;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public final class ImageConstants {
    private ImageConstants(){}

    public static final String [] JPEG_FILE_ENDINGS = {"jpg", "jpeg", "jpe", "jif", "jfif", "jfi"};
    public static final String [] PNG_FILE_ENDINGS = {"png"};
    public static final String [] BMP_FILE_ENDINGS = {"bmp", "dib"};

    public static ArrayList<String> getAggregateEndingsList(){
        ArrayList<String> aggList = new ArrayList<>();
        aggList.addAll(List.of(JPEG_FILE_ENDINGS));
        aggList.addAll(List.of(PNG_FILE_ENDINGS));
        aggList.addAll(List.of(BMP_FILE_ENDINGS));
        return aggList;
    }
    //TODO: Add a function to get
    public static ArrayList<String> getAggregateEndingsListForExtensionFilter(){
        ArrayList<String> rawEndings = getAggregateEndingsList();
        ArrayList<String> agg = new ArrayList<>();
        for(String string: rawEndings){
            agg.add(String.format("*.%s", string));
            agg.add(String.format("*.%s", string.toUpperCase()));
        }
        return agg;
    }

    public static final FileFilter javaFXImageFileFilter = new FileFilter() {
        public boolean isValidJavaFXImageFile(File file){
            String fileName = file.getName();
            int extensionStart = fileName.lastIndexOf(".");
            if(extensionStart + 1 >= fileName.length() || extensionStart == -1){
                return false;
            }
            String fileExtension = fileName.substring(extensionStart+1);
            //sometimes file extensions are in caps, so the lower case is necessary
            return getAggregateEndingsList().contains(fileExtension.toLowerCase());
        }
        public boolean accept(File pathname){
            return isValidJavaFXImageFile(pathname);
        }
    };
}
