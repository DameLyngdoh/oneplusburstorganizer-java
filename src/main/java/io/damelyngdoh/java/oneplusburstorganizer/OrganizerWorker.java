package io.damelyngdoh.java.oneplusburstorganizer;

import java.io.File;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.SwingWorker;

/**
 * This class is a SwingWorker implementation and is used by the GUI class MainWindow 
 * to execute the OrganizerService operation in the background while a dialog is visible 
 * to the user allowing user to cancel the operation.
 * 
 * @author Dame Lyngdoh
 * @version 1.0.0
 * @since 2019-05-24
 */
public class OrganizerWorker extends SwingWorker<Void, Void> {
    
    /**
     * Dialog in main application window to be visible while organizer thread is still running
     */
    private final JDialog dialog;
    
    /**
     * 
     */
    private OrganizerService organizer = null;
    
    /**
     * Constructs new object with source and destination directory along with the 
     * dialog to be visible during organizing operation.
     * @param srcDir Source directory
     * @param Destination directory
     * @param Dialog in main application window to be visible during thread execution
     */
    public OrganizerWorker(File srcDir, File destDir, JDialog dialog) {
        this.dialog = dialog;
        this.organizer = new OrganizerService(srcDir, destDir);
    }
	
	@Override
	protected Void doInBackground() throws Exception {
		this.organizer.organize();
		return null;
	}
	
	@Override
    protected void done() {
    	if(this.dialog!=null) {
    		this.dialog.setVisible(false);
    	}
    }
	
	/**
	 * Returns the results from the organizer service
	 * @return list of results
	 */
	public List<Result> getResults(){
		return this.organizer.getResults();
	}
}
