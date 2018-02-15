package pl.beng.thesis.outlook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.beng.thesis.outlook.model.Message;
import pl.beng.thesis.outlook.service.MailService;
import pl.beng.thesis.outlook.util.OutlookMessageMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping(value = "outlook")
public class MailController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MailService mailService;
    private final RestTemplate restTemplate;
    private final OutlookMessageMapper outlookMessageMapper;

    @Autowired
    public MailController(MailService mailService,
                          RestTemplate restTemplate,
                          OutlookMessageMapper outlookMessageMapper) {

        this.mailService = mailService;
        this.restTemplate = restTemplate;
        this.outlookMessageMapper = outlookMessageMapper;
    }

    @RequestMapping(value = "/mails", method = RequestMethod.GET)
    public ResponseEntity<List<Message>> receiveLast25Messages(@RequestHeader(value = "auth_token") String authToken) throws URISyntaxException {

        // Generate URI
        URI url = mailService.generateURIForMailReadLast25Messages();

        //Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authToken);
        List<Message> messageList = null;

        try {
            ResponseEntity<String> responseMessages = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
            if(responseMessages.getStatusCode().value() == 200) {
                messageList = outlookMessageMapper.from(responseMessages.getBody());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public ResponseEntity<String> createAndSendMail(@RequestHeader(value = "auth_token") String authToken,
                                            @RequestBody String message) throws URISyntaxException {

        URI url = mailService.generateURIForMailSending();

        //Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authToken);
        headers.add("Content-Type", "application/json");

        HttpEntity<String> request = new HttpEntity<>(message, headers);
        try {
            restTemplate.postForObject(url, request, String.class);
        } catch (Exception ex) {
            logger.info("Exception occurred: " + ex);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

}
