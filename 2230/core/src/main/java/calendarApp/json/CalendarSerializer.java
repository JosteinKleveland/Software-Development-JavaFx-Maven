package calendarApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;

// Serializer for calendar objects
public class CalendarSerializer extends JsonSerializer<Calendar> {


    /*
     * Serializes a calendar object on the format:
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

      // Serializes the calendar's appointments through the appointment serializer
      for (Appointment appointment : calendar.getAppointments()) {
        jGen.writeObject(appointment);
      }
      jGen.writeEndArray();
      jGen.writeEndObject();
    }
  }