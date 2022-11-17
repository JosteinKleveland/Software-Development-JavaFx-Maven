package calendarApp.ui;

import calendarApp.core.Calendar;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import calendarApp.core.Appointment;
import calendarApp.core.CalendarLogic;
import calendarApp.json.CalendarSaveHandler;

/**
 * Class that centralizes access to a CalendarLogic
 * Makes it easier to support transparent use of a REST api
 */
public class DirectCalendarLogicAccess implements CalendarLogicAccess {
    
    private final CalendarLogic calendarLogic;

    public DirectCalendarLogicAccess(CalendarLogic calendarLogic) {
        this.calendarLogic = new CalendarLogic(calendarLogic.getCurrentCalendar());
    }

    public Calendar getCurrentCalendar(String... calendarName) {
        try {
            return CalendarSaveHandler.load(calendarName[0]);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCurrentCalendar(Calendar currentCalendar) {
        calendarLogic.setCurrentCalendar(currentCalendar);
        try {
            CalendarSaveHandler.save(currentCalendar);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteCalendar(String name) {
        CalendarSaveHandler.delete(getCurrentCalendar().getCalendarName());
    }

    public void renameCalendar(String oldName, String newName) {
        Calendar calendar = calendarLogic.getCurrentCalendar();
        if (calendar == null) {
        throw new IllegalArgumentException("No Calendar named \"" + oldName + "\" found");
        }
        if (calendarLogic.getCurrentCalendar(newName) != null) {
        throw new IllegalArgumentException("A Calendar named \"" + newName + "\" already exists");
        }
        calendar.setCalendarName(newName);
    }

    @Override
    public boolean isValidCalendarName(String name) {
        return calendarLogic.isValidCalendarName(name);
    }
}
