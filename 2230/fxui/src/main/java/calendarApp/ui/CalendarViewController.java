package calendarApp.ui;

import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class CalendarViewController {
    
    @FXML private Button btnExitCalendar;
    @FXML private Button btnNewAppointment;
    @FXML private Button btnDeleteCalendar;

    @FXML private Label lblCalendarNamePreview;
    @FXML private Label lblFeedback;

    @FXML private GridPane gridShowCalendar;

    private CalendarLogic calendarLogic;
    private Calendar currentCalendar;


    public void viewCalendar(Calendar calendar) {
        this.calendarLogic = new CalendarLogic();
    }

    public void deleteCalendar() {

    }

    public void openAppointment() {

    }

    public void exitCalendar() {

    }

    public void createNewAppointment() {

    }
}
