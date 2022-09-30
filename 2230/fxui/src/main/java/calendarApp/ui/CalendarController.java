package calendarApp.ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import core.src.main.java.calendarApp.json.CalendarSaveHandler;

public class CalendarController {
    private CalendarSaveHandler calendarSaveHandler = new CalendarSaveHandler();

    //Declare FXML elements from Calendar.fxml
    @FXML private Button saveName_btn;
    @FXML private TextField calendarName_txt;
    @FXML private Text outputField_txt;

    //Method called by clicking on the "save" button in the GUI
    @FXML
    public void saveName() throws IllegalArgumentException {
        //Getting the text input from the text field
        String calendarName = calendarName_txt.getText();

        //Checking whether the text field is empty or not
        if(calendarName.length() == 0){
            outputField_txt.setText("Calendar name cannot be empty");
            throw new IllegalArgumentException("Calendar name cannot be empty");
        }

        //Checks whether the calendar name exists from before.
        if(calendarSaveHandler.checkIfFileExists(calendarName)) {
            outputField_txt.setText("Calendar name already exists, choose another");
            throw new IllegalArgumentException("Calendar name exists from before");
        }

        try {
            //Text field is not empty, and the save handling is forwarded to CalendarSaveHandler
            CalendarSaveHandler.save(calendarName);
            outputField_txt.setText("Calendar name is saved");
            calendarName_txt.setText("");
        }
        catch (IOException e) {
            outputField_txt.setText("Could not save calendar name");
            e.printStackTrace();
        }
    }
}
