module calendarApp.ui {
    
    requires com.fasterxml.jackson.databind;


    requires javafx.controls;
    requires javafx.fxml;

    requires calendarApp.core;

    opens calendarApp.ui to javafx.graphics, javafx.fxml;
}
