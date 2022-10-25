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
        return deserialize((JsonNode) treeNode);
    } 

    public Appointment deserialize(JsonNode jsonNode) throws IOException, JsonProcessingException {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            String appointmentName = "";
            String appointmentDescription = "";
            DaysOfTheWeek day = null;
            int startHour = 0;
            int stopHour = 0;
            int startMinute = 0;
            int stopMinute = 0;
            JsonNode nameNode = objectNode.get("appointmentName");
            if (nameNode instanceof TextNode) {
                appointmentName = ((TextNode) nameNode).asText();
            } 
            JsonNode descriptionNode = objectNode.get("appointmentDescription");
            if (descriptionNode instanceof TextNode) {
                appointmentDescription = ((TextNode) descriptionNode).asText();
            } 

            JsonNode weekdayNode = objectNode.get("dayOfTheWeek");
            if (weekdayNode instanceof TextNode) {
                String weekday = ((TextNode) weekdayNode).asText();
                for (DaysOfTheWeek dayOfTheWeek : DaysOfTheWeek.values()) {
                    if (weekday.toUpperCase().equals(dayOfTheWeek.toString())) {
                        day = dayOfTheWeek;
                    }
                }
            }
            JsonNode startHourNode = objectNode.get("startHour");
            if (startHourNode instanceof NumericNode) {
                startHour = ((NumericNode) startHourNode).asInt();
            } 
            JsonNode stopHourNode = objectNode.get("stopHour");
            if (stopHourNode instanceof NumericNode) {
                stopHour = ((NumericNode) stopHourNode).asInt();
            } 
            JsonNode startMinuteNode = objectNode.get("startMin");
            if (startMinuteNode instanceof NumericNode) {
                startMinute = ((NumericNode) startMinuteNode).asInt();
            } 
            JsonNode stopMinuteNode = objectNode.get("stopMin");
            if (stopMinuteNode instanceof NumericNode) {
                stopMinute = ((NumericNode) stopMinuteNode).asInt();
            } 

            if(day != null) {
                Appointment appointment = new Appointment(appointmentName, appointmentDescription, day, startHour, stopHour, startMinute, stopMinute);
                return appointment;
            }
        }
        return null;
    } 
}