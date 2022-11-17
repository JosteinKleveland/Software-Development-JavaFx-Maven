package calendarApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import calendarApp.core.Appointment;
import calendarApp.core.Calendar;
import calendarApp.core.CalendarLogic;

// Deseralizer for the creation of calendarLogic objects
public class CalendarLogicDeserializer extends JsonDeserializer<CalendarLogic> {

    private AppointmentDeserializer appointmentDeserializer = new AppointmentDeserializer();

    /*
     * Deserializes a calendarLogic through a json tree node on the format: 
     * { 
     *    "calendarName": "..."
     *    "appointments": [...]
     * }
     */

    @Override
    public CalendarLogic deserialize(JsonParser parser, DeserializationContext ctext) throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        if (treeNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) treeNode;
            String calendarName = "";
            JsonNode nameNode = objectNode.get("calendarName");
            if (nameNode instanceof TextNode) {
                calendarName = ((TextNode) nameNode).asText();
            } 

            Calendar calendar = new Calendar(calendarName);

            //Utilizes the appointment deserializer and adds appointments returned to the calendar            
            JsonNode appointmentsNode = objectNode.get("appointments");
            if (appointmentsNode instanceof ArrayNode) {
                for (JsonNode elementNode : ((ArrayNode) appointmentsNode)) {
                    Appointment appointment = appointmentDeserializer.deserialize(elementNode);
                    if (appointment != null) {
                        calendar.addAppointment(appointment);
                    }
                }
            }
            return new CalendarLogic(calendar);
        }
        return null;
    }
}