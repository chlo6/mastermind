public class FileUtils {
	
	/** 
	 * Opens a file to read using the Scanner class.
	 * @param fileName	name of the file to open
	 * @return 			the Scanner object to the file
	 */
	 
	 public static java.util.Scanner openToRead(String fileName) {
		 java.util.Scanner input = null;
		 
		 try { 
			input = new java.util.Scanner(new java.io.File(fileName));
		 }
		 catch (java.io.FileNotFoundException e) {
			System.err.println("ERROR: Cannot open " + fileName +
							" for reading.");
			System.exit(4); 
		}									
		 
		 return input;
	 }
	 
	 /**
	  * Opens a file to write using the PrintWriter class.
	  * @param fileName		name fo the file to open
	  * @return 			the PrintWriter object to the file
	  */
	  
	  public static java.io.PrintWriter openToWrite(String fileName) {
		  java.io.PrintWriter output = null;
		  try {
			  output = new java.io.PrintWriter(new java.io.File(fileName));
		  }
		  catch (java.io.FileNotFoundException e) {
			  System.err.println("ERROR: Cannot open " + fileName + 
								"for writing.");
			  System.exit(5);
		  }
		  return output;
	  }
}
