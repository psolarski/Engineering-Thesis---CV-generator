package pl.beng.thesis.controller;

import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.beng.thesis.model.Developer;
import pl.beng.thesis.model.Education;
import pl.beng.thesis.model.Project;
import pl.beng.thesis.model.Skill;
import pl.beng.thesis.service.DeveloperService;

import java.util.List;

@Controller
@RequestMapping(value = "developers")
public class DeveloperController {

    private final DeveloperService developerService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
     * Find and return developer with specified username
     * @param username developer's username
     * @return Developer with given username
     */
    @RequestMapping(value = "/developer/{username}", method = RequestMethod.GET)
    public ResponseEntity<Developer> findOneByUsername(@PathVariable("username") String username) {

        return new ResponseEntity<>(developerService.findByUsername(username), HttpStatus.OK);
    }


    /**
     * Create new developer.
     * @param newDeveloper developer to persist.
     * @return created developer.
     */
    @RequestMapping(value = "/developer", method = RequestMethod.POST)
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer newDeveloper) {

        return new ResponseEntity<>(developerService.createDeveloper(newDeveloper), HttpStatus.OK);
    }

    /**
     * Generate and return pdf for Developer with specified id
     *
     * @param username developer username
     * @return Generated PDF as byte array
     * @throws DocumentException when service was unable to create document
     */
    @RequestMapping(value = "/developer/{username}/cv", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<byte[]> generateDeveloperCv(@PathVariable("username") String username) throws DocumentException {

        byte[] processedHtml = developerService.generateDeveloperCv(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
        headers.add("Access-Control-Allow-Headers", "Content-Type");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Content-Disposition", "inline; filename=cv.pdf");
        headers.setContentLength(processedHtml.length);

        logger.info("DOCUMENT LENGHT " + processedHtml.length);

        return new ResponseEntity<>(processedHtml, headers, HttpStatus.CREATED);
    }

    /**
     * Add new education to existing Developer
     * @param username of developer
     * @param newEducation new Education
     * @return Http status OK
     */
    @RequestMapping(value = "/developer/{username}/educations/education", method = RequestMethod.POST)
    public ResponseEntity<Developer> addNewSkill(@PathVariable(value = "username") String username,
                                      @RequestBody Education newEducation) {

        return new ResponseEntity<>(this.developerService.addNewSkill(username, newEducation), HttpStatus.OK);
    }



    @RequestMapping(value = "/developer/{username}/projects/project", method = RequestMethod.POST)
    public ResponseEntity<Developer> addNewProject(@PathVariable(value = "username") String username,
                                                 @RequestBody Project project) {

        return new ResponseEntity<>(this.developerService.addNewProject(username, project), HttpStatus.OK);
    }

    @RequestMapping(value = "/developer/{username}/skills/", method = RequestMethod.POST)
    public ResponseEntity<Developer> addSkills(@PathVariable(value = "username") String username,
                                               @RequestBody List<Skill> skills) {

        return new ResponseEntity<>(this.developerService.addNewSkills(username, skills), HttpStatus.OK);
    }
}
