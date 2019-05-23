/*
 * 
 */

package io.damelyngdoh.java.oneplusburstorganizer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JDialog;

import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

/**
 * @author Dame Lyngdoh
 *
 */
public class OrganizerThread extends SwingWorker<Void, Void>{
	
	/**
	 * File variables for source and destination directory
	 */
    private File srcDir, destDir;
    
    /**
     * Lists to hold newly created directories and directories which were unable to be created
     */
    private final List<File> newDirs = new ArrayList<File>(), 
    		failedDirs = new ArrayList<File>();
    
    private boolean loggingEnabled = true;
    
    /**
     * Dialog in main application window to be visible while organizer thread is still running
     */
    private final JDialog dialog;
    
    /**
     * List to hold results of organizer thread
     */
    private List<Result> results = new ArrayList<Result>();

    /**
     * 
     * @param srcDir Source directory
     * @param Destination directory
     * @param Dialog in main application window to be visible during thread execution
     */
    public OrganizerThread(File srcDir, File destDir, JDialog dialog) {
        this.setSrcDir(srcDir);
        this.destDir = destDir==null ? srcDir : destDir;
        this.dialog = dialog;
    }

    /**
     * @return the source directory
     */
    public File getSrcDir() {
        return srcDir;
    }

    /**
     * @param srcDir the srcDir to set
     */
    public void setSrcDir(File srcDir) {
        this.srcDir = srcDir;
    }

    /**
     * @return the destination directory
     */
    public File getDestDir() {
        return destDir;
    }

    /**
     * @param destDir the destDir to set
     */
    public void setDestDir(File destDir) {
        this.destDir = destDir;
    }

    /**
     * @return the list of new directories created
     */
    public List<File> getNewDirs() {
        return newDirs;
    }

    /**
     * @return the list of directories failed to be created
     */
    public List<File> getFailedDirs() {
        return this.failedDirs;
    }

    /**
     * @return the loggingEnabled
     */
    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    /**
     * @param loggingEnabled the loggingEnabled to set
     */
    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    /**
     * 
     * @return the list of Result object
     */
    public List<Result> getResults(){
        return this.results;
    }
    
    /**
     * Common method for logging
     */
    private void log(Object message, Throwable t, int type) {
        if(!this.loggingEnabled) {
            return;
        }
    }

    @Override
    protected Void doInBackground() throws Exception {

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
                }
                // Else add to failedDir list
                else {
                    this.failedDirs.add(newDir);
                }
            }
            else {
                batchArray = bucket.get(batchId);
            }
            batchArray.add(f);			
        }

        // Moving files to respective directory, only for newDir list
        for(File f : this.newDirs) {
            List<File> fileList = bucket.get(f.getName());
            for(File file : fileList) {
                boolean success = true;
                // Copying file to new directory, preserving date
                try {
                    FileUtils.copyFileToDirectory(file, f, true);
                } catch (IOException e) {
                    results.add(new Result(file, f, 3));
                    success = false;
                }
                // Removing old file
                try {
                    FileUtils.forceDelete(file);
                } catch (IOException e) {
                    results.add(new Result(file, f, 4));
                    success = false;
                }
                if (success) {
                    results.add(new Result(file, f, 1));
                }
            }
        }
        for(File f : this.failedDirs) {
            List<File> fileList = bucket.get(f.getName());
            for(File file : fileList) {
                results.add(new Result(file, f, 2));
            }
        }
        return null;
    }
	
    @Override
    protected void done() {
        this.dialog.setVisible(false);
    }
    
    /**
     * Filter class to select only files containing specific file name
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

