
package io.damelyngdoh.java.oneplusburstorganizer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Service class for organizing burst image files in source directory and organizes them in separate 
 * directories within destination directory. Currently the supported burst image filename format is 
 * IMG_<image-id>_<burst-id>_<sequence-id>.
 * 
 * @author Dame Lyngdoh
 * @version 1.0.0
 * @since 2019-05-24
 */
public class OrganizerService {
	
	/**
	 * File variables for source and destination directory
	 */
    private File srcDir, destDir;
    
    /**
     * Lists to hold newly created directories and directories which were unable to be created
     */
    private final List<File> newDirs = new ArrayList<File>(), 
    		failedDirs = new ArrayList<File>();
    
    /**
     * List to hold results of organizer thread
     */
    private List<Result> results = new ArrayList<Result>();

    /**
     * Constructs new OrganizerService object with source and destination directories specified.
     * @param srcDir Source directory
     * @param Destination directory
     * @param Dialog in main application window to be visible during thread execution
     */
    public OrganizerService(File srcDir, File destDir) {
        this.setSrcDir(srcDir);
        this.destDir = destDir==null ? srcDir : destDir;
    }

    /**
     * Returns the source directory for the service object.
     * @return the source directory
     */
    public File getSrcDir() {
        return srcDir;
    }

    /**
     * Sets the source directory for the service object.
     * @param srcDir the srcDir to set
     */
    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    /**
     * Returns the destination directory for the service object.
     * @return the destination directory
     */
    public File getDestDir() {
        return destDir;
    }

    /**
     * Sets the destination directory for the service object.
     * @param destDir the destDir to set
     */
    public void setDestDir(File destDir) {
        this.destDir = destDir;
    }

    /**
     * Returns the list of new directories created during the organization operation.
     * @return the list of new directories created
     */
    public List<File> getNewDirs() {
        return newDirs;
    }

    /**
     * Returns the list of directories which were failed to be created.
     * @return the list of directories failed to be created
     */
    public List<File> getFailedDirs() {
        return this.failedDirs;
    }

    /**
     * Returns the result list for the service. Will be useful if called after organize method is called.
     * @return the list of Result object
     */
    public List<Result> getResults(){
        return this.results;
    }

    /**
     * Performs the organization operation using the specified source and destination directories.
     */
    public void organize() {

        // Listing all files in parent directory based on filter policy
        File[] files = this.srcDir.listFiles(new Filter());

        // File buckets
        HashMap<String, List<File>> bucket = new HashMap<String, List<File>>();

        // Adding files to buckets based on batchId
        for(File f : files) {

            // Sorting files into buckets based on batchId
            String batchId = f.getName().split("_")[2];
            List<File> batchArray = null;
            if(!bucket.containsKey(batchId)) {
                batchArray = new ArrayList<File>();
                bucket.put(batchId, batchArray);
                
                // Creating new directory with the batchId as name in the target directory
                File newDir = new File(this.destDir.getPath() + "\\" + batchId);
                // If directory creation successful add to newDir list
                if(newDir.mkdir()) {
                    this.newDirs.add(newDir);
                    System.out.println("Directory " + newDir.getName() + " created successfully.");
                }
                // Else add to failedDir list
                else {
                    this.failedDirs.add(newDir);
                    System.out.println("Directory " + newDir.getName() + " creation failed.");
                }
            }
            else {
                batchArray = bucket.get(batchId);
            }
            batchArray.add(f);			
        }

        System.out.println("Directory creation complete");
        
        // Moving files to respective directory, only for newDir list
        for(File f : this.newDirs) {
            List<File> fileList = bucket.get(f.getName());
            for(File file : fileList) {
                boolean success = true;
                Result re = null;
                // Copying file to new directory, preserving date
                try {
                    FileUtils.copyFileToDirectory(file, f, true);
                } catch (IOException e) {
                	re = new Result(file, f, 3);
                    success = false;
                }
                // Removing old file
                try {
                    FileUtils.forceDelete(file);
                } catch (IOException e) {
                    re = new Result(file, f, 4);
                	success = false;
                }
                if (success) {
                    re = new Result(file, f, 1);
                }
                results.add(re);
                System.out.println(re);
            }
        }
        for(File f : this.failedDirs) {
            List<File> fileList = bucket.get(f.getName());
            for(File file : fileList) {
            	Result re = new Result(file, f, 2);
            	results.add(re);
            	System.out.println(re);
            }
        }
    }
    
    /**
     * Filter class to select only files containing specific file name
     * @see FilenameFilter
     */
    class Filter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            if(dir==null) {
                return false;
            }
            if(name==null || name.length()==0) {
                return false;
            }
            String[] tokens = name.split("_");
            return tokens.length == 4;
        }
    }
}

