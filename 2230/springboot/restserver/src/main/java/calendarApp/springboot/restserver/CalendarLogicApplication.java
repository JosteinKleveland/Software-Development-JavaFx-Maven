package calendarApp.springboot.restserver;

import com.fasterxml.jackson.databind.Module;
import java.util.EnumSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import calendarApp.json.CalendarSaveHandler;
//import todolist.json.TodoPersistence.TodoModelParts;

/**
 * The Spring application.
 */
@SpringBootApplication
public class CalendarLogicApplication {
    //Ser ikke helt hva denne metoden gjør
    //Den tar EnumSet.of av en enum metode i TodoPersistence
    /*@Bean
  public Module objectMapperModule() {
    return TodoPersistence.createJacksonModule(EnumSet.of(TodoModelParts.LISTS));
  }*/

  public static void main(String[] args) {
    SpringApplication.run(CalendarLogicApplication.class, args);
  }

}
