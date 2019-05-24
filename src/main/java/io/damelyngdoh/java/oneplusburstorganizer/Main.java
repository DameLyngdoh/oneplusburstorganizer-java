package io.damelyngdoh.java.oneplusburstorganizer;

import java.io.File;

/**
 * This is the main class for the project.
 * 
 * @author Dame Lyngdoh
 * @version 1.0.0
 * @since 2019-05-24
 */
public class Main {

	/**
	 * Main method for the project.
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		
		System.out.println(args.length);
		
		// Check for command line arguments
		if(args.length==2 || (args.length==1 && args[0].equalsIgnoreCase("-h"))) {
			
			// Check if arguments are valid or not
			File src = new File(args[0]);
			File dst = args.length==1 ? src : new File(args[1]);
			
			// Validating existence of directories
			if(!src.exists()) {
				System.out.println("Error: source directory does not exists - " + src.getPath());
				return;
			}
			if(!dst.exists()) {
				System.out.println("Error: destination directory does not exists - " + dst.getPath());
				return;
			}
			
			// Validating type of file
			if(!src.isDirectory()) {
				System.out.println("Error: source is not a directory - " + src.getPath());
				return;
			}
			if(!src.isDirectory()) {
				System.out.println("Error: destination is not a directory - " + dst.getPath());
				return;
			}
			
			// Validating permissions
			if(!src.canWrite()) {
				System.out.println("Error: write permission denied for source directory - " + src.getPath());
				return;
			}
			if(!dst.canWrite()) {
				System.out.println("Error: write permission denied for destination directory - " + dst.getPath());
				return;
			}
			
			OrganizerService organizer = new OrganizerService(src, dst);
			organizer.organize();
		}
		
		// Else use graphical user-interface
		else if (args.length==0){
			new MainWindow().setVisible(true);
		}
		else {
			System.out.println("Usage: java -jar <organizer-jar-filename> source-directory [destination-directory]");
		}
	}
	
	/**
	 * Returns a human readable message in String for a numerical status indicator
	 * @param status the integer indicating the status
	 * @return A String message of the status
	 */
	public static String getStatus(int status) {
		String result = null;
		switch(status){
        case 1:
            result = "Success";
            break;
        case 2:
            result = "Could not create directory";
            break;
        case 3:
            result = "Could not copy file to destination";
            break;
        case 4:
            result = "Copy complete but ile not deleted";
            break;
		}
		return result;
	}
}
