package CalendarApp.core;

import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CalendarController {
    private FileManagement fileManagement = new FileManagement();

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

        try {
            //Text field is not empty, and the save handling is forwarded to FileManagement
            fileManagement.save(calendarName);
            outputField_txt.setText("Calendar name is saved");
            calendarName_txt.setText("");
        }
        catch (FileNotFoundException e) {
            outputField_txt.setText("Could not save calendar name");
            e.printStackTrace();
        }
    }
}
