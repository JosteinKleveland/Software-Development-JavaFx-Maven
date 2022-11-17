module calendarApp.springboot.restserver {
    requires com.fasterxml.jackson.databind;

    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.boot.autoconfigure;

    requires calendarApp.core;

    opens calendarApp.springboot.restserver to spring.beans, spring.context, spring.web;
}
