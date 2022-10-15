package calendarApp.json;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import calendarApp.core.Calendar;

public class CalendarAppPersistence {

    private ObjectMapper mapper;

    public CalendarAppPersistence() {
        mapper.registerModule(new CalendarAppModule());
    }

    public Calendar readCalendar(Reader reader) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(reader, Calendar.class);
    }

    public void writeCalendar(Calendar calendar, Writer writer) throws JsonGenerationException, JsonMappingException, IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, calendar); 
    }
}