package pl.beng.thesis.outlook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import pl.beng.thesis.outlook.model.TokenResponse;
import pl.beng.thesis.outlook.service.AuthHelperService;

import java.net.URI;
import java.net.URISyntaxException;


@RestController
@CrossOrigin
@RequestMapping(value = "outlook")
public class AuthorizationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final RestTemplate restTemplate;
    private final AuthHelperService authHelperService;

    @Autowired
    public AuthorizationController(AuthHelperService authHelperService, RestTemplate restTemplate) {
        this.authHelperService = authHelperService;
        this.restTemplate = restTemplate;
    }

    /**
     * Create and redirect to authorization outlook 365 authorization.
     *
     * @return RedirectionView to created URI
     * @throws URISyntaxException when uri syntax is wrong
     */
    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public RedirectView handleAuthorization() throws URISyntaxException {

        logger.info(authHelperService.getLoginUrl().toString());

        return new RedirectView(authHelperService.getLoginUrl().toString());
    }

    /**
     * Redeem authorization code and acquire
     * access token from Office 365 resource.
     *
     * @param code given from Azure AD Authentication
     * @throws URISyntaxException when uri syntax is wrong
     */
    @RequestMapping(value = "/authorization/redirection", method = RequestMethod.POST)
    public void handleAuthenticationToken(@RequestParam("code") String code ) throws URISyntaxException {

        URI url = authHelperService.getAccessTokenUrl();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);

        final HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(authHelperService.createAccessTokenBody(code), header);

        ResponseEntity<TokenResponse> responseEntity = restTemplate.exchange(url,
                                                                      HttpMethod.POST,
                                                                      entity,
                                                                      TokenResponse.class);
    }
}
