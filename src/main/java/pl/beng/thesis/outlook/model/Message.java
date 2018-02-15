package pl.beng.thesis.outlook.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private String id;
    private ZonedDateTime receivedDateTime;
    private Recipient from;
    private Boolean isRead;
    private String subject;
    private String bodyPreview;

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getReceivedDateTime() {
        return receivedDateTime;
    }

    public void setReceivedDateTime(ZonedDateTime receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    public Recipient getFrom() {
        return from;
    }

    public void setFrom(Recipient from) {
        this.from = from;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyPreview() {
        return bodyPreview;
    }

    public void setBodyPreview(String bodyPreview) {
        this.bodyPreview = bodyPreview;
    }
}
