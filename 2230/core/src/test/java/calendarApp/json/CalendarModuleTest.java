package calendarApp.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;

import org.junit.jupiter.api.DisplayName;

public class CalendarModuleTest {

    private static ObjectMapper mapper;
    private final static String calendarWithTwoAppointments = "{\"calendarName\":\"jsonTest\",\"appointments\":[{\"appointmentName\":\"Fotball\",\"dayOfTheWeek\":\"WEDNESDAY\",\"startHour\":7,\"stopHour\":9,\"startMin\":0,\"stopMin\":30},{\"appointmentName\":\"Math\",\"dayOfTheWeek\":\"THURSDAY\",\"startHour\":\"11,\"stopHour\":\"12,\"startMin\":\"30,\"stopMin\":\"0}]}";

    @BeforeAll
    public static void testSetup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new CalendarAppModule());
    }

    @Test
    @DisplayName("Check that serializers for Appointment and Calendar-objects work correctly")
    public void testSerializers() {
        Calendar calendar = new Calendar("jsonTest");
        Appointment appointment1 = new Appointment("Fotball", DaysOfTheWeek.WEDNESDAY, 7, 9, 0, 30);
        calendar.addAppointment(appointment1);          
        Appointment appointment2 = new Appointment("Math", DaysOfTheWeek.THURSDAY, 11, 12, 30, 0);
        calendar.addAppointment(appointment2);
        try {
            assertEquals("{\"calendarName\":\"jsonTest\",\"appointments\":[{\"appointmentName\":\"Fotball\",\"dayOfTheWeek\":\"WEDNESDAY\",\"startHour\":7,\"stopHour\":9,\"startMin\":0,\"stopMin\":30},{\"appointmentName\":\"Math\",\"dayOfTheWeek\":\"THURSDAY\",\"startHour\":11,\"stopHour\":12,\"startMin\":30,\"stopMin\":0}]}", 
            mapper.writeValueAsString(calendar));
        } catch (JsonProcessingException e) {
            fail();
        }
    }

    public void checkAppointmentFormat(Appointment appointment, String appointmentName, DaysOfTheWeek day, int startHour, int stopHour, int startMin, int stopMin) {
        assertEquals(appointmentName, appointment.getAppointmentName());
        assertEquals(day, appointment.getDayOfTheWeek());
        assertEquals(startHour, appointment.getStartHour());
        assertEquals(stopHour, appointment.getStopHour());
        assertEquals(startMin, appointment.getStartMinute());
        assertEquals(stopMin, appointment.getStopMinute());
    }

    @Test
    @DisplayName("Check that deserializers for Appointment and Calendar reads from json correctly, and creates Objects")
    public void testDeserializers() {
        try {
            Calendar calendar2 = mapper.readValue(calendarWithTwoAppointments, Calendar.class);
            assertTrue(calendar2.getAppointments().size() == 2);
            for (Appointment appointment : calendar2.getAppointments()) {
                
                if(appointment.getAppointmentName() == "Fotball") {
                    checkAppointmentFormat(appointment, "Fotball", DaysOfTheWeek.WEDNESDAY, 7, 9, 0, 30);
                }
                else if(appointment.getAppointmentName() == "Math") {
                    checkAppointmentFormat(appointment, "Math", DaysOfTheWeek.THURSDAY, 11, 12, 30, 0);
                }
                else {
                    fail();
                }
           }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (AssertionError e2) {
            e2.printStackTrace();
        }
    }
}
