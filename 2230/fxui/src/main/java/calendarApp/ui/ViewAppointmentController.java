package calendarApp.ui;

import calendarApp.core.Appointment;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ViewAppointmentController {
    
    @FXML Label lblAppointmentName;
    @FXML Label lblPreviewStartTime;
    @FXML Label lblPreviewStopTime;
    @FXML Label lblAppointmentDescription;
    
    @FXML Button btnEditAppointment;
    @FXML Button btnDeleteAppointment;

    private CalendarViewController calendarViewController;
    private Appointment appointmentInView;

    public void viewAppointment(CalendarViewController calendarViewController, Appointment appointmentInView) {
        this.calendarViewController = calendarViewController;
        this.appointmentInView = appointmentInView;
    }

    public void deleteAppointment() {

    }

    public void editAppointment() {

    }

    public void exitView() {

    }
}
