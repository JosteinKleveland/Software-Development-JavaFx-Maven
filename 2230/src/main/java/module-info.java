module CalendarApp.core {

    requires javafx.controls;
    requires javafx.fxml;

    opens CalendarApp.core to javafx.graphics, javafx.fxml;
    
}
