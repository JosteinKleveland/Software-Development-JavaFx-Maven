package CalendarApp.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//Handles saving and loading of calendars.
public class CalendarSaveHandler {
    
    //Stores the string of the path to the directory where calendars are stored - in a CONSTANT.
    public final static String SAVE_FOLDER = "/workspace/gr2230/2230/src/main/java/CalendarApp/core/savedCalendars/";


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
    public static void save(String filename) throws IOException {
        JSONObject calendarJSONObject = new JSONObject();
        calendarJSONObject.put("name", filename);

        FileWriter file = new FileWriter(getFilePath(filename));
        file.write(calendarJSONObject.toJSONString());
        file.close();
    }   

    /**
     * @param filename - the name of the calendar one wants to retrieve.
     * @throws FileNotFoundException - throws when the calendar name is not found.
     * 
     * For now, only reads the name of the calendar.
     */
    public static void load(String filename) throws FileNotFoundException {
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader(filename))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject =  (JSONObject) obj;
            String name = (String) jsonObject.get("name");
            System.out.println(name);

        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 


    /**
     * @return an ArrayList of all filenames in the savedCalendars directory.
     */
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

    //For practical testing.
    public static void main(String[] args) throws IOException {
        CalendarSaveHandler fileHandler = new CalendarSaveHandler();
        CalendarSaveHandler.save("wamarlea");
        System.out.println(fileHandler.checkIfFileExists("endremor"));

        System.out.println(fileHandler.getAllFileNames());
    }
}