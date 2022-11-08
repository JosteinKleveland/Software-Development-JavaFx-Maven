package calendarApp.springboot.restserver;

import com.fasterxml.jackson.databind.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import calendarApp.json.CalendarSaveHandler;

/**
 * The Spring application.
 */
@SpringBootApplication
public class CalendarLogicApplication {
    @Bean
  public Module objectMapperModule() {
    return CalendarSaveHandler.createJacksonModule();
  }

  public static void main(String[] args) {
    SpringApplication.run(CalendarLogicApplication.class, args);
  }

}
