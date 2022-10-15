package calendarApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import calendarApp.core.Appointment;

public class AppointmentSerializer extends JsonSerializer<Appointment> {


    /*
     * {
     *     "AppointmentName"
     *      "dayOfTheWeek"
     *      "start hour"
     *      "start min"
     *      "end hour"
     *      "end min"    
     * }
     */

    @Override
    public void serialize(Appointment appointment, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
      jGen.writeStartObject();
      jGen.writeStringField("appointmentName", appointment.getAppointmentName());
      jGen.writeStringField("dayOfTheWeek", appointment.getDayOfTheWeek().toString());
      jGen.writeNumberField("startHour", appointment.getStartHour());
      jGen.writeNumberField("stopHour", appointment.getStopHour());
      jGen.writeNumberField("startMin", appointment.getStartMinute());
      jGen.writeNumberField("stopMin", appointment.getStopMinute());
      jGen.writeEndObject();
    }
  }