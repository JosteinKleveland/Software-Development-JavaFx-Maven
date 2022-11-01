module calendarApp.restapi {
    //requires transitive com.fasterxml.jackson.databind;

    exports calendarApp.restapi;

    //requires transitive calendarApp.core;

    requires jakarta.ws.rs;

    requires jersey.common;
    requires jersey.server;
    requires jersey.media.json.jackson;

    requires org.glassfish.hk2.api;
    requires org.slf4j;

    requires calendarApp.core;

    opens calendarApp.restapi to jersey.server;
}
