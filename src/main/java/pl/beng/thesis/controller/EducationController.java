package pl.beng.thesis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.beng.thesis.model.Education;
import pl.beng.thesis.service.EducationService;

import java.util.List;

@RestController
@RequestMapping(value = "educations")
public class EducationController {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    /**
     * Find Education by given id.
     *
     * @param id given id.
     * @return education with given id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Education> find(@PathVariable(value = "id") long id) {

        return new ResponseEntity<>(educationService.find(id), HttpStatus.OK);
    }

    /**
     * Find and return educations list.
     *
     * @return education list.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Education>> findAll() {

        return new ResponseEntity<>(educationService.findAll(), HttpStatus.OK);
    }

    /**
     * Update existing education by given education.
     *
     * @param updatedEducation given updated education.
     * @return Education object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Education> updateEducation(@RequestBody Education updatedEducation) {

        return new ResponseEntity<>(educationService.update(updatedEducation), HttpStatus.OK);
    }


    /**
     * Create new education.
     *
     * @param education given education to persist
     * @return created education.
     */
    @RequestMapping(value = "/education", method = RequestMethod.POST)
    public ResponseEntity<Education> createEducation(@RequestBody Education education) {

        return new ResponseEntity<>(educationService.create(education), HttpStatus.OK);
    }
}
