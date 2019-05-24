package io.damelyngdoh.java.oneplusburstorganizer;

import java.io.File;

/**
 * This class represents a result for the organizing operation for 
 * a specific file.
 * 
 * @author Dame Lyngdoh
 * @version 1.0.0
 * @since 2019-05-24
 */
public class Result {
    private final File imageFile, dir;
    private final int status;
    
    /**
     * Constructs new Result object with image file and destination 
     * directory along with status of the organization operation for 
     * the file. 
     * @param imageFile
     * @param dir
     * @param status
     */
    public Result(File imageFile, File dir, int status){
        this.imageFile = imageFile;
        this.dir = dir;
        this.status = status;
    }
    
    /**
     * Returns the image file
     * @return File object
     */
    public File getImageFile(){
        return this.imageFile;
    }
    
    /**
     * Returns the destination directory
     * @return Destination directory
     */
    public File getDir() {
        return this.dir;
    }
    
    /**
     * Returns the status of the organizing operation for the specific 
     * image file.
     * @return Integer denoting the status
     */
    public int getStatus(){
        return this.status;
    }
    
    @Override
    public String toString() {
    	return this.imageFile.getName() + " - " + this.dir + " - " + Main.getStatus(this.status);
    }
}
