package calendarApp.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import calendarApp.core.Calendar;

import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.InputStreamReader;


//Handles saving and loading of calendars.
public class CalendarSaveHandler {
    
    //Stores the string of the path to the directory where calendars are stored - in a CONSTANT.
    public final static String SAVE_FOLDER = "/workspace/gr2230/2230/data/src/main/java/calendarApp/data/savedCalendars/";

    /**
     * @param filename - filename of calendar to be retrieved.
     * @return path to .json calendar file.
     */
    public static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".json";
	}

    /**
     * @param filename - name of calendar one wants to see if exists (name of calendar is same as its filename (w.out .filetype)).
     * @return - true if the calendar exists, false if it doesn't.
     */
    public boolean checkIfFileExists(String filename) {
        File file = new File(getFilePath(filename));
            if(file.isFile()) { 
                return true;
            }
            else {
                return false;
            }
    }

    /**
     * @param filename - the name of the calendar to be saved / stored.
     * @throws IOException - throws IOException
     * 
     * Saves the name of the filename provided in a .json file.
     * If it doens't exist from before, builds a .json file with the filename (calendar name) provided and stores the name inside.
     */
    public static void save(Calendar calendar) throws IOException, JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new CalendarAppModule());
        
        String json = mapper.writeValueAsString(calendar);
        
    }   
    
    /**
     * @param filename - the name of the calendar one wants to retrieve.
     * @throws FileNotFoundException - throws when the calendar name is not found.
     * 
     * For now, only reads the name of the calendar.
     */
    public static void load(String filename) throws FileNotFoundException {
        
    } 


    /**
     * @return an ArrayList of all filenames in the savedCalendars directory.
     */
    public ArrayList<String> getAllFileNames() {
		ArrayList<String> filenames = new ArrayList<String>();

        ResourceBundle bundle = ResourceBundle.getBundle(SAVE_FOLDER);
        String saveFolderPath = bundle.getString(SAVE_FOLDER);

		//File[] files = new File(SAVE_FOLDER).listFiles();
        File[] files = new File(saveFolderPath).listFiles();
        //If this pathname does not contain a directory, then listFiles() returns null. 
        if (files == null) {
            return new ArrayList<String>();
        }
		
		for (File file : files) {
		    if (file.isFile()) {
		        filenames.add(file.getName());
		    }
		}
		
		return filenames;
	}

    //For practical testing.
    public static void main(String[] args) throws IOException {
        CalendarSaveHandler fileHandler = new CalendarSaveHandler();
        CalendarSaveHandler.save("wamarlea");
        System.out.println(fileHandler.checkIfFileExists("endremor"));

        System.out.println(fileHandler.getAllFileNames());
    }
}