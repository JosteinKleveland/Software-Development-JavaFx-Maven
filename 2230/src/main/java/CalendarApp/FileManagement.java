import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class saveHandler {

    public final static String SAVE_FOLDER = "src/main/java/CalenderApp/savedCalendars/";

    // For now, it will only save the name.
    public void save(String filename) throws FileNotFoundException {
        try(PrintWriter writer = new PrintWriter(getFilePath(filename))) {
            writer.println(filename);
        }
    }

    public static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}

    public static void main(String[] args) throws FileNotFoundException {
        saveHandler test = new saveHandler();
        test.save("testfile");
        System.out.println("test");
    }
    
	public ArrayList<String> getAllFileNames() {
		ArrayList<String> filenames = new ArrayList<String>();


		File[] files = new File(SAVE_FOLDER).listFiles();
		//If this pathname does not contain a directory, then listFiles() returns null. 
		
		for (File file : files) {
		    if (file.isFile()) {
		        filenames.add(file.getName());
		    }
		}
		
		return filenames;
	}
}
