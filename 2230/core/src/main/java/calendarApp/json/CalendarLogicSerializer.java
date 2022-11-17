package calendarApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import calendarApp.core.Appointment;
import calendarApp.core.CalendarLogic;

// Serializer for calendarLogic objects
public class CalendarLogicSerializer extends JsonSerializer<CalendarLogic> {


    /*
     * Serializes a calendarLogic object on the format:
     * {
     *     "calendarName": 
     *     "appointments": [...]  
     * }
     */

    @Override
    public void serialize(CalendarLogic calendarLogic, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
      jGen.writeStartObject();
      jGen.writeStringField("calendarName", calendarLogic.getCurrentCalendar().getCalendarName());
      jGen.writeArrayFieldStart("appointments");

      // Serializes the calendar's appointments through the appointment serializer
      for (Appointment appointment : calendarLogic.getCurrentCalendar().getAppointments()) {
        jGen.writeObject(appointment);
      }
      jGen.writeEndArray();
      jGen.writeEndObject();
    }
  }