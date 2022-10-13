package calendarApp.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import calendarApp.core.Appointment;
import calendarApp.core.DaysOfTheWeek;

public class AppointmentDeserializer extends JsonDeserializer<Appointment> {

    @Override
    public Appointment deserialize(JsonParser parser, DeserializationContext ctext) throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        if (treeNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) treeNode;
            String appointmentName = "";
            DaysOfTheWeek day = null;
            int startHour = 0;
            int startMinute = 0;
            int stopHour = 0;
            int stopMinute = 0;
            JsonNode nameNode = objectNode.get("appointmentName");
            if (nameNode instanceof TextNode) {
                appointmentName = ((TextNode) nameNode).asText();
            } 
            JsonNode weekdayNode = objectNode.get("dayOfTheWeek");
            if (weekdayNode instanceof TextNode) {
                String weekday = ((TextNode) weekdayNode).asText();
                if (weekday == "Monday") {
                    day = DaysOfTheWeek.MONDAY;
                }
                else if (weekday == "Tuesday") {
                    day = DaysOfTheWeek.TUESDAY;
                }
                else if (weekday == "Wedensday") {
                    day = DaysOfTheWeek.WEDENSDAY;
                }
                else if (weekday == "Thursday") {
                    day = DaysOfTheWeek.THURSDAY;
                }
                else if (weekday == "Friday") {
                    day = DaysOfTheWeek.FRIDAY;
                }
                else if (weekday == "Saturday") {
                    day = DaysOfTheWeek.SATURDAY;
                }
                else if (weekday == "Sunday") {
                    day = DaysOfTheWeek.SUNDAY;
                }
            } 
            JsonNode startHourNode = objectNode.get("startHour");
            if (startHourNode instanceof NumericNode) {
                startHour = ((NumericNode) startHourNode).asInt();
            } 
            JsonNode startMinuteNode = objectNode.get("startMinute");
            if (startMinuteNode instanceof NumericNode) {
                startMinute = ((NumericNode) startMinuteNode).asInt();
            } 
            JsonNode stopHourNode = objectNode.get("stopHour");
            if (stopHourNode instanceof NumericNode) {
                stopHour = ((NumericNode) stopHourNode).asInt();
            } 
            JsonNode stopMinuteNode = objectNode.get("stopMinute");
            if (stopMinuteNode instanceof NumericNode) {
                stopMinute = ((NumericNode) stopMinuteNode).asInt();
            } 

        }
        return null;
    } 
}