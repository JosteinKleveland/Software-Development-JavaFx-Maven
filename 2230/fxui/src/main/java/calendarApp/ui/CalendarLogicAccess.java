package calendarApp.ui;

import calendarApp.core.Calendar;

/**
 * Class that centralizes access to a CalendarLogic
 * Makes it easier to support transparent use of a REST api
 */
public interface CalendarLogicAccess {
    
    /**
     * Method to get the calendar currently
     * stored in the calendarLogic object
     * 
     * @param calendarName, optional calendarName
     * @return the current calendar object
     */
    public Calendar getCurrentCalendar(String... calendarName);

    /**
     * Save the current calendar
     * 
     * @param currentCalendar, the calendar to save
     */
    public void setCurrentCalendar(Calendar currentCalendar);

    /**
     * Delete the current calendar
     * 
     * @param name, the name of the calendar to delete
     */
    public void deleteCalendar(String name);

    /**
     * Rename the gived calendar
     * 
     * @param oldName, the old name of the calendar
     * @param newName, the new name for the calendar
     */
    public void renameCalendar(String oldName, String newName);

    /**
     * Checks whether the calendar name is valid
     * 
     * @param name
     * @return true if name is valid
     */
    public boolean isValidCalendarName(String name);
}