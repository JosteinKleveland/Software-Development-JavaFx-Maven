package calendarApp.ui;

import calendarApp.core.Appointment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

//Manages Appointments shown in grid
public class AppointmentController {

    @FXML private Label lblAppointmentName;
    @FXML private Label lblAppointmentTime;

    @FXML
    private void click(MouseEvent mouseEvent){
        calendarListener.onClickListener(appointment);
    }


    private Appointment appointment;
    private CalendarListener calendarListener;

    protected void setData(Appointment appointment, CalendarListener calendarListener){

        this.appointment = appointment;
        this.calendarListener = calendarListener;
        lblAppointmentName.setText(appointment.getAppointmentName());
        lblAppointmentTime.setText(appointment.getStartHour()+":"+appointment.getStartMinute()+" - "+appointment.getStopHour()+":"+ appointment.getStopMinute());   

    }

    public Appointment getAppointment() {
        return this.appointment;
    }

}
