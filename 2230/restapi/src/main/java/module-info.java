module calendarApp.restapi {
    //requires transitive com.fasterxml.jackson.databind;

    exports calendarApp.restapi;

    requires jakarta.ws.rs;

    requires jersey.common;
    requires jersey.server;
    requires jersey.media.json.jackson;

    requires org.glassfish.hk2.api;
    requires org.slf4j;

}
