package calendarApp.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.DaysOfTheWeek;

class CalendarAppModule extends SimpleModule {
    private static final String NAME = "CalendarAppModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {};
  
    public CalendarAppModule() {
      super(NAME, VERSION_UTIL.version());
      addSerializer(Appointment.class, new AppointmentSerializer());
      addSerializer(Calendar.class, new CalendarSerializer());

    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new CalendarAppModule());
        Calendar calendar = new Calendar("jsonTest");
        Appointment appointment1 = new Appointment("Fotball", DaysOfTheWeek.WEDENSDAY, 7, 9, 0, 30);
        calendar.addAppointment(appointment1);
        Appointment appointment2 = new Appointment("Math", DaysOfTheWeek.THURSDAY, 11, 12, 30, 0);
        try {
            System.out.println(mapper.writeValueAsString(calendar));
        } catch (JsonProcessingException e) {
            System.out.println("Virket ikke");
            e.printStackTrace();
        }
    }
  }
