package CalendarApp.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CalendarSaveHandler {
    
    public final static String SAVE_FOLDER = "/workspace/gr2230/2230/src/main/java/CalendarApp/core/savedCalendars/";

    public static String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".json";
	}

    public static void save(String filename) throws FileNotFoundException {
        JSONObject calendarJSONObject = new JSONObject();
        calendarJSONObject.put("name", filename);

        try {
            FileWriter file = new FileWriter(getFilePath(filename));
            file.write(calendarJSONObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }  
    }   

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

    public static void main(String[] args) throws FileNotFoundException {
        CalendarSaveHandler fileHandler = new CalendarSaveHandler();
        CalendarSaveHandler.save("wamarlea");

        System.out.println(fileHandler.getAllFileNames());
    }
}