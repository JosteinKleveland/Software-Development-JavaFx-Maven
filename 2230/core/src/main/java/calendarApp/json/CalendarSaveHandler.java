package calendarApp.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;

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
        File calendarJson = new File(getFilePath(calendar.getCalendarName()));
        if (calendarJson.createNewFile()) {
            System.out.println("File created: " + calendarJson.getName());
          } else {
            System.out.println("File already exists.");
          }
        
        FileWriter writer = new FileWriter(calendarJson.getName());
        writer.write(json);
        writer.close();
    }   
    
    /**
     * @param filename - the name of the calendar one wants to retrieve.
     * @throws FileNotFoundException - throws when the calendar name is not found.
     * 
     * For now, only reads the name of the calendar.
     */
    public static Calendar load(String filename) throws FileNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new CalendarAppModule());

        File calendar = new File(getFilePath(filename));
        Scanner reader = new Scanner(calendar);
        Calendar myCalendar = mapper.readValue(reader, Calendar.class);
        
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
        Calendar calendar = new Calendar("Steinar");
        Appointment appointment = new Appointment("Musikk", DaysOfTheWeek.MONDAY, 8, 9, 0, 0);
        calendar.addAppointment(appointment);
        CalendarSaveHandler.save(calendar);
        System.out.println(fileHandler.checkIfFileExists("Steinar"));

        //ERROR
        // System.out.println(fileHandler.getAllFileNames());
    }
}