package calendarApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;

public class CalendarSerializer extends JsonSerializer<Calendar> {


    /*
     * {
     *     "calendarName":
     *     "appointments": [...]   
     * }
     */

    @Override
    public void serialize(Calendar calendar, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
      jGen.writeStartObject();
      jGen.writeStringField("calendarName", calendar.getCalendarName());
      jGen.writeArrayFieldStart("appointments");
      for (Appointment appointment : calendar.getAppointments()) {
        jGen.writeObject(appointment);
      }
      jGen.writeEndArray();
      jGen.writeEndObject();
    }
  }