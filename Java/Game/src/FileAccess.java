import java.io.File;
import java.util.Scanner;

/**
 * This class is used to access files safely.
 * 
 * @author Oliver Mance
 * @version 1.0
 */
public class FileAccess {

	/**
	 * Safely attempts to access a file.
	 * 
	 * @param path The path of the file.
	 * @return A Scanner object of the accessed file null if an exception occurred.
	 */
	public static Scanner getFile(String path) {
		try {
			File f = new File(path);
			Scanner in = new Scanner(f);
			return in;
		} catch (Exception e) {
			System.out.println("ERROR: File not found");
			e.printStackTrace();
			return null;
		}
	}
}
