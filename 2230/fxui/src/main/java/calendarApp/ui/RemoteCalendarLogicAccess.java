package calendarApp.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;

import javax.net.ssl.HttpsURLConnection;

import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;
import calendarApp.json.CalendarAppModule;

/**
 * Class that centralizes access to a CalendarLogicAccess. Makes it easier to support transparent use of a
 * REST API.
 */
public class RemoteCalendarLogicAccess implements CalendarLogicAccess {
    
    private HttpRequest request = null;
    private final URI endpointBaseUri;
    private ObjectMapper objectMapper;
    private CalendarLogic calendarLogic;


    public RemoteCalendarLogicAccess(URI endpointBaseUri) {
        this.endpointBaseUri = endpointBaseUri;
        objectMapper.registerModule(new CalendarAppModule());
    }

    private CalendarLogic getCalendarLogic() {
        if (calendarLogic == null) {
            this.request = HttpRequest.newBuilder(endpointBaseUri)
                .header("Accept", "application/json")
                .GET()
                .build();
        }
        try {
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            this.calendarLogic = objectMapper.readValue(response.body(), CalendarLogic.class);
            //Can also get a settings-class if we choose to use one.
            //getCalendarSettings();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return calendarLogic;
    }

    //Possible methods to use for settings
    /*

    private boolean isDefaultSettings(TodoSettings todoSettings) {
       return todoSettings == null;
    }

    @Override
    public TodoSettings getTodoSettings() {
        TodoModel todoModel = getTodoModel();
        TodoSettings settings = todoModel.getSettings();
        if (isDefaultSettings(settings)) {
        HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("settings"))
            .header("Accept", "application/json")
            .GET()
            .build();
        try {
            final HttpResponse<String> response =
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            final String responseString = response.body();
            settings = objectMapper.readValue(responseString, TodoSettings.class);
            todoModel.setSettings(settings);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        }
        return settings;
    }                                    */

    /**
     * Checks that name is valid for a (new) Calendar.
     *
     * @param name the (new) name
     * @return true if the name is value, false otherwise
     */

    public boolean isValidCalendarName(String calendarName) {
        return getCalendarLogic().isValidCalendarName(calendarName);
    }
    
    //Her hadde de en metode som var public boolean hasTodoList(String name).. Vet ikke om vi trenger noe sånt

    //Her hadde de en metode som returne en liste over alle todolistene sine. 

    private String uriParam(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
    private URI calendarUri(String calendarName) {
        return endpointBaseUri.resolve("/").resolve(uriParam(calendarName));
    }/*
    private URI todoListUri(String name) {
        return endpointBaseUri.resolve("list/").resolve(uriParam(name));
    }*/


    @Override
    public Calendar getCurrentCalendar(String calendarName) {
        Calendar oldCalendar = this.calendarLogic.getCurrentCalendar(calendarName);
        // if existing calendar has no appointments, try to (re)load
        if (oldCalendar == null || (! (oldCalendar instanceof Calendar))) {
            HttpRequest request = 
                HttpRequest.newBuilder(calendarUri(calendarName))
                    .header("Accept", "application/json").GET().build();
        try {
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            Calendar calendar = objectMapper.readValue(responseString, Calendar.class);
            if (! (calendar instanceof Calendar)) {
                Calendar newCalendar = new Calendar(calendar.getCalendarName());
                calendar = newCalendar;
            }
            this.calendarLogic.setCurrentCalendar(calendar);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        }
        return oldCalendar;
    }

    /** Helper-method for setCurrentCalender to not give direct access. 
     * 
     * @param calendar to be set 
     */
    private void putCurrentCalendar(Calendar calendar) { 
        try {
            String json = objectMapper.writeValueAsString(calendar);
            HttpRequest request = HttpRequest.newBuilder(calendarUri(calendar.getCalendarName()))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(BodyPublishers.ofString(json))
                .build();
            final HttpResponse<String> response = 
                HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            Boolean added = objectMapper.readValue(responseString, Boolean.class);
            if (added != null) {
                calendarLogic.setCurrentCalendar(calendar);
            }
        }
        catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
   }

    /**
     * Adds a Calendar to the underlying CalendarLogic.
     *
     * @param Calendar to set
     */
    @Override
    public void setCurrentCalendar(Calendar calendar) {
        putCurrentCalendar(calendar);
    }


    // ----------------- Kommet ned hit -----------------------

    /**
     * Removes the TodoList with the given name from the underlying TodoModel.
     *
     * @param name the name of the TodoList to remove
     */
    @Override
    public void removeTodoList(String name) {
        try {
        HttpRequest request = HttpRequest.newBuilder(todoListUri(name))
            .header("Accept", "application/json")
            .DELETE()
            .build();
        final HttpResponse<String> response =
            HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        Boolean removed = objectMapper.readValue(responseString, Boolean.class);
        if (removed != null) {
            todoModel.removeTodoList(todoModel.getTodoList(name));
        }
        } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
        }
    }

    /**
     * Renames a TodoList to a new name.
     *
     * @param oldName the name of the TodoList to change
     * @param newName the new name
     */
    @Override
    public void renameTodoList(String oldName, String newName) {
        try {
        HttpRequest request = HttpRequest.newBuilder(todoListUri(oldName))
            .header("Accept", "application/json")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(BodyPublishers.ofString("newName=" + uriParam(newName)))
            .build();
        final HttpResponse<String> response =
            HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
        String responseString = response.body();
        Boolean renamed = objectMapper.readValue(responseString, Boolean.class);
        if (renamed != null) {
            todoModel.getTodoList(oldName).setName(newName);
        }
        } catch (IOException | InterruptedException e) {
        throw new RuntimeException(e);
        }
    }

    /**
     * Notifies that the TodoList has changed, e.g. TodoItems
     * have been mutated, added or removed.
     *
     * @param todoList the TodoList that has changed
     */
    @Override
    public void notifyTodoListChanged(AbstractTodoList todoList) {
        putTodoList(todoList);
    }
}

