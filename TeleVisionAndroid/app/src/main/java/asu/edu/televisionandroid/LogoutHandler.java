package asu.edu.televisionandroid;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

public class LogoutHandler {

    private static final String IMAGE_DIRECTORY_NAME = "MayoClinicStorage";


    public void deleteStoredFiles() {
        System.out.println("Here");

        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        // check for directory
        if (mediaStorageDir.isDirectory()) {
            // getting list of file paths
            File[] listFiles = mediaStorageDir.listFiles();
            // Check for count
            if (listFiles.length > 0) {
                for (int i = 0; i < listFiles.length; i++) {
                    String filePath = listFiles[i].getAbsolutePath();
                    File tempFile = new File(filePath);
                    tempFile.delete();
                    System.out.println(filePath + " deleted");
                }
            }
        }
    }

}
