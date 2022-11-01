package calendarApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import calendarApp.core.Appointment;


 // Serializer for appointment objects
public class AppointmentSerializer extends JsonSerializer<Appointment> {


    /**
     * Serializes an appointment on the format:
     * {
     *     "appointmentName": "...",
     *     "appointmentDescription": "...",
     *     "dayOfTheWeek": "...",
     *     "startHour": <int>,
     *     "stopHour": <int>,
     *     "startMin": <int>,
     *     "stopMin": <int>
     * }
     * @param appointment
     * @param jGen 
     * @param serializerProvider
     */

    @Override
    public void serialize(Appointment appointment, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
      jGen.writeStartObject();
      jGen.writeStringField("appointmentName", appointment.getAppointmentName());
      jGen.writeStringField("appointmentDescription", appointment.getAppointmentDescription());
      jGen.writeStringField("dayOfTheWeek", appointment.getDayOfTheWeek().toString());
      jGen.writeNumberField("startHour", appointment.getStartHour());
      jGen.writeNumberField("stopHour", appointment.getStopHour());
      jGen.writeNumberField("startMin", appointment.getStartMinute());
      jGen.writeNumberField("stopMin", appointment.getStopMinute());
      jGen.writeEndObject();
    }
  }