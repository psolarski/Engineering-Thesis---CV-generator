package pl.beng.thesis.outlook.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import pl.beng.thesis.outlook.exception.InvalidExternalApiResponseException;
import pl.beng.thesis.outlook.model.EmailAddress;
import pl.beng.thesis.outlook.model.Message;
import pl.beng.thesis.outlook.model.Recipient;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OutlookMessageMapper {

    public List<Message> from(String responseJson) throws InvalidExternalApiResponseException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages;

        try {
            JsonNode node = mapper.readTree(responseJson);

            messages = readMessagesFromJsonResponse(node.withArray("value"));
        } catch (Exception ex) {
            throw new InvalidExternalApiResponseException("Response from Outlook API was malformed!", ex);
        }
        return messages;
    }

    private List<Message> readMessagesFromJsonResponse(JsonNode rootNode) {

        List<Message> messages = new ArrayList<>();
        rootNode.forEach(msg -> {

            // Create objects
            Message message = new Message();
            EmailAddress emailAddress = new EmailAddress();
            Recipient recipient = new Recipient();

            // Map text fields
            message.setBodyPreview(msg.get("bodyPreview").asText());
            message.setId(msg.get("id").asText());
            message.setSubject(msg.get("subject").asText());
            message.setRead(msg.get("isRead").asBoolean());

            // Map date
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(msg.get("receivedDateTime").asText());
            message.setReceivedDateTime(zonedDateTime);

            // Map email address and recipient
            emailAddress.setAddress(msg.get("from").get("emailAddress").get("address").asText());
            emailAddress.setName(msg.get("from").get("emailAddress").get("name").asText());

            recipient.setEmailAddress(emailAddress);

            message.setFrom(recipient);
            messages.add(message);
        });
        return messages;
    }
}
