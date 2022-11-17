module calendarApp.ui {
    
    requires com.fasterxml.jackson.databind; //For Jackson
    requires java.net.http; //For HTTP-commands

    requires javafx.controls;
    requires javafx.fxml;

    requires calendarApp.core; //Requires access to core-module

    opens calendarApp.ui to javafx.graphics, javafx.fxml;
}
