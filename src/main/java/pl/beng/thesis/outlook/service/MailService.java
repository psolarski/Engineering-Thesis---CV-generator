package pl.beng.thesis.outlook.service;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class MailService {

    public URI generateURIForMailReadLast25Messages() throws URISyntaxException {
        return new URIBuilder("https://graph.microsoft.com/v1.0/me/mailfolders/inbox/messages?$select=receivedDateTime,from,isRead,subject,bodyPreview&$top=25&$orderby=receivedDateTime%20DESC").build();
    }


    public URI generateURIForMailSending() throws URISyntaxException {
        return new URIBuilder("https://graph.microsoft.com/v1.0/me/sendmail").build();
    }
}
