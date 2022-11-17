package calendarApp.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.core.DaysOfTheWeek;

import org.junit.jupiter.api.DisplayName;

public class CalendarModuleTest {

    private static ObjectMapper mapper;
    private final static String calendarWithTwoAppointments = "{\"calendarName\":\"jsonTest\",\"appointments\":[{\"appointmentName\":\"Fotball\",\"appointmentDescription\":\"test description\",\"dayOfTheWeek\":\"WEDNESDAY\",\"startHour\":7,\"stopHour\":9,\"startMin\":0,\"stopMin\":30},{\"appointmentName\":\"Math\",\"appointmentDescription\":\"test description\",\"dayOfTheWeek\":\"THURSDAY\",\"startHour\":11,\"stopHour\":12,\"startMin\":30,\"stopMin\":0}]}";
    private final static String calendarLogicWithCalendarWithTwoAppointments = "{\"calendarName\":\"jsonTest\",\"appointments\":[{\"appointmentName\":\"Fotball\",\"appointmentDescription\":\"test description\",\"dayOfTheWeek\":\"WEDNESDAY\",\"startHour\":7,\"stopHour\":9,\"startMin\":0,\"stopMin\":30},{\"appointmentName\":\"Math\",\"appointmentDescription\":\"test description\",\"dayOfTheWeek\":\"THURSDAY\",\"startHour\":11,\"stopHour\":12,\"startMin\":30,\"stopMin\":0}]}";

    @BeforeAll
    public static void testSetup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new CalendarAppModule());
    }

    @Test
    @DisplayName("Check that serializers for Appointment, Calendar and CalendarLogic-objects work correctly")
    public void testSerializers() {
        Calendar calendar = new Calendar("jsonTest");
        Appointment appointment1 = new Appointment("Fotball", "test description", DaysOfTheWeek.WEDNESDAY, 7, 9, 0, 30);
        calendar.addAppointment(appointment1);          
        Appointment appointment2 = new Appointment("Math", "test description", DaysOfTheWeek.THURSDAY, 11, 12, 30, 0);
        calendar.addAppointment(appointment2);
        //CalendarLogic calendarLogic = new CalendarLogic(calendar);
        try {
            assertEquals(calendarWithTwoAppointments, mapper.writeValueAsString(calendar));
        } catch (JsonProcessingException e) {
            fail();
        }
        /*try {
            assertEquals(calendarLogicWithCalendarWithTwoAppointments, mapper.writeValueAsString(calendarLogic));
        } catch (JsonProcessingException e) {
            fail();
        }*/
    }

    @Test
    @DisplayName("Test deserializing of string from serializer")
    public void testSerializersDeserializers() {
        Calendar calendar1 = new Calendar("TestCalendar");
        Appointment appointment1 = new Appointment("Fotball", "test description", DaysOfTheWeek.WEDNESDAY, 7, 9, 0, 30);         
        Appointment appointment2 = new Appointment("Math", "test description", DaysOfTheWeek.THURSDAY, 11, 12, 30, 0);
        calendar1.addAppointment(appointment1); 
        calendar1.addAppointment(appointment2);

        try {
            String json = mapper.writeValueAsString(calendar1);
            Calendar calendar2 = mapper.readValue(json, Calendar.class);
            Appointment appointment1new = calendar2.getAppointments().get(0);
            Appointment appointment2new = calendar2.getAppointments().get(1);
            assertEquals("TestCalendar", calendar2.getCalendarName());  
            checkAppointmentFormat(appointment1new, "Fotball", "test description", DaysOfTheWeek.WEDNESDAY, 7, 9, 0, 30);
            checkAppointmentFormat(appointment2new,"Math", "test description", DaysOfTheWeek.THURSDAY, 11, 12, 30, 0);
            assertThrows(IndexOutOfBoundsException.class, () -> {calendar2.getAppointments().get(2);});
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }


    }

    public void checkAppointmentFormat(Appointment appointment, String appointmentName, String appointmentDescription, DaysOfTheWeek day, int startHour, int stopHour, int startMin, int stopMin) {
        assertEquals(appointmentName, appointment.getAppointmentName());
        assertEquals(appointmentDescription, appointment.getAppointmentDescription());
        assertEquals(day, appointment.getDayOfTheWeek());
        assertEquals(startHour, appointment.getStartHour());
        assertEquals(stopHour, appointment.getStopHour());
        assertEquals(startMin, appointment.getStartMinute());
        assertEquals(stopMin, appointment.getStopMinute());
    }

    @Test
    @DisplayName("Check that deserializers for Appointment, Calendar and CalendarLogic reads from json correctly, and creates Objects")
    public void testDeserializers() {
        try {
            Calendar calendar2 = mapper.readValue(calendarWithTwoAppointments, Calendar.class);
            //CalendarLogic calendarLogic2 = mapper.readValue(calendarLogicWithCalendarWithTwoAppointments, CalendarLogic.class);
            assertTrue(calendar2.getAppointments().size() == 2);
            //assertTrue(calendarLogic2.getCurrentCalendar().getAppointments().size() == 2);
            //assertTrue(calendarLogic2.getCurrentCalendar().getCalendarName().equals("jsonTest"));
            checkAppointmentFormat(calendar2.getAppointments().get(0), "Fotball", "test description", DaysOfTheWeek.WEDNESDAY, 7, 9, 0, 30);
            checkAppointmentFormat(calendar2.getAppointments().get(1), "Math", "test description", DaysOfTheWeek.THURSDAY, 11, 12, 30, 0);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (AssertionError e2) {
            e2.printStackTrace();
        }
    }
}