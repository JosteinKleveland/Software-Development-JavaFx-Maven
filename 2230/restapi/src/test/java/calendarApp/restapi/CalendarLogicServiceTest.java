package calendarApp.restapi;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
//import java.util.Iterator;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.restapi.CalendarLogicService;
import calendarApp.restserver.CalendarConfig;
import calendarApp.restserver.CalendarModuleObjectMapperProvider;
import calendarApp.json.CalendarLogicSerializer;
import calendarApp.json.CalendarLogicDeserializer;

public class CalendarLogicServiceTest extends JerseyTest {
    /*
    protected boolean shouldLog() {
        return true;
    }
    
    @Override
    protected ResourceConfig configure() {
        final CalendarConfig config = new CalendarConfig();
        if (shouldLog()) {
            enable(TestProperties.LOG_TRAFFIC);
            enable(TestProperties.DUMP_ENTITY);
            config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
        }
        return config;
    }

    private ObjectMapper objectMapper;

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
        objectMapper = new CalendarModuleObjectMapperProvider().getContext(getClass());
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testGet_calendar() {
        Response getResponse = target(CalendarLogicService.CALENDAR_LOGIC_SERVICE_PATH)
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .get();
        assertEquals(200, getResponse.getStatus());
        try {
            CalendarLogic calendarLogic = objectMapper.readValue(getResponse.readEntity(String.class), CalendarLogic.class);
            Calendar calendarTest = calendarLogic.getCurrentCalendar();
            //Iterator<Calendar> it = CalendarLogic.iterator();
            //trenger ikke iterere når vi kun har en kalender i logic?
            assertEquals("calendarTest", calendarTest.getCalendarName());
            //assertEquals("todo2", todoList2.getName());
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGet_calendar_calendarTest() {
        Response getResponse = target(CalendarLogicService.CALENDAR_LOGIC_SERVICE_PATH)
            .path("calendar")
            .path("calendarTest")
            .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8")
            .get();
        assertEquals(200, getResponse.getStatus());
        try {
            Calendar calendar = objectMapper.readValue(getResponse.readEntity(String.class), Calendar.class);
            assertEquals("calendarTest", calendar.getCalendarName());
            assertTrue(calendar instanceof Calendar);
            //assertTrue(((Calendar) calendar).iterator().hasNext());
            //Igjen, skal vel ikke iterere. Med mindre det er over appointments?
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }*/
}
