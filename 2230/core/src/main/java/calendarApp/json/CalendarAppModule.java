package calendarApp.json;


import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.module.SimpleModule;
import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
/* For running main-method
import calendarApp.core.DaysOfTheWeek;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
*/


class CalendarAppModule extends SimpleModule {
    private static final String NAME = "CalendarAppModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {};
  
    public CalendarAppModule() {
      super(NAME, VERSION_UTIL.version());
      addSerializer(Appointment.class, new AppointmentSerializer());
      addSerializer(Calendar.class, new CalendarSerializer());
      addDeserializer(Appointment.class, new AppointmentDeserializer());
      addDeserializer(Calendar.class, new CalendarDeserializer());
    }

    /*
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new CalendarAppModule());
        Calendar calendar = new Calendar("jsonTest");
        Appointment appointment1 = new Appointment("Fotball", DaysOfTheWeek.WEDNESDAY, 7, 9, 0, 30);
        calendar.addAppointment(appointment1);          
        Appointment appointment2 = new Appointment("Math", DaysOfTheWeek.THURSDAY, 11, 12, 30, 0);
        calendar.addAppointment(appointment2);
        try {
            System.out.println(mapper.writeValueAsString(calendar));
        } catch (JsonProcessingException e) {
            System.out.println("Virket ikke");
            e.printStackTrace();
        }

        try {
            String json = mapper.writeValueAsString(calendar);
            Calendar calendar2 = mapper.readValue(json, Calendar.class);
            for (Appointment appointment : calendar2.getAppointments()) {
                System.out.println(appointment);
            }
        } catch (JsonProcessingException e) {
            System.out.println("Virket ikke");
            e.printStackTrace();
        }
    }
    */
  }
