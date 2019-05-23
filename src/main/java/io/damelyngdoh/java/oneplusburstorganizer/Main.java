/**
 * 
 */
package io.damelyngdoh.java.oneplusburstorganizer;

import java.io.File;

/**
 * @author Dame
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
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
			
			OrganizerThread organizer = new OrganizerThread(src, dst, null);
		}
		
		// Else use gui
		else if (args.length==0){
			
		}
		else {
			System.out.println("");
		}
	}

}
