package pl.beng.thesis.controller;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.beng.thesis.model.Developer;
import pl.beng.thesis.service.DeveloperService;

import java.util.List;

@Controller
@RequestMapping(value = "cv-generator/developers")
public class DeveloperController {

    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    /**
     * Find and return list of developers
     *
     * @return all developers list
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Developer>> findAll() {

        return new ResponseEntity<>(developerService.findAll(), HttpStatus.OK);
    }

    /**
     * Find and return developer with specified id
     *
     * @param id developer id
     * @return Developer with given id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Developer> findOne(@PathVariable("id") long id) {

        return new ResponseEntity<>(developerService.find(id), HttpStatus.OK);
    }

    /**
     * Generate and return pdf for Developer with specified id
     *
     * @param id developer id
     * @return Generated PDF as byte array
     * @throws DocumentException when service was unable to create document
     */
    @RequestMapping(value = "/{id}/cv", method = RequestMethod.POST)
    public ResponseEntity<byte[]> generateDeveloperCv(@PathVariable("id") long id) throws DocumentException {

        byte[] processedHtml = developerService.generateDeveloperCv(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Content-Disposition", "Developer-CV");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");

        headers.setContentLength(processedHtml.length);
        return new ResponseEntity<>(processedHtml, headers, HttpStatus.CREATED);
    }
}
