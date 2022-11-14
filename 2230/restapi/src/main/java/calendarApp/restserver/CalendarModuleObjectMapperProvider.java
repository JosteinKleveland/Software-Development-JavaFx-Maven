package calendarApp.restserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;
import calendarApp.json.CalendarSaveHandler;

/**
 * Provides the Jackson module used for JSON serialization.
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CalendarModuleObjectMapperProvider implements ContextResolver<ObjectMapper> {

  private final ObjectMapper objectMapper;
  
  /**
   * Constructor
   * creates an ObjectMapper with CalendarAppModule
   * in the CalendarSaveHandler file
   */
  public CalendarModuleObjectMapperProvider() {
    objectMapper = CalendarSaveHandler.createObjectMapper();
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {
    return objectMapper;
  }
}
