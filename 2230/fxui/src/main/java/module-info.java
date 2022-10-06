module calendarApp.ui {
    

    requires javafx.controls;
    requires javafx.fxml;

    requires calendarApp.core;

    opens calendarApp.ui to javafx.graphics, javafx.fxml;
}
