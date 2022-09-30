module name {
    //module CalendarApp.core {

    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens CalendarApp.core to javafx.graphics, javafx.fxml, json.simple;
            
}
