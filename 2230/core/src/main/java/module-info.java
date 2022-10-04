module calendarApp.core {
    //module CalendarApp.core {

    requires json.simple;
    
    exports calendarApp.core;
    exports calendarApp.json;

    opens json.simple;
}
