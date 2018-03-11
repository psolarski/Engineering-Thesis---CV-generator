package pl.beng.thesis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.beng.thesis.model.Skill;
import pl.beng.thesis.service.SkillService;

import java.util.List;

@RestController
@RequestMapping(value = "skills")
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    /**
     * Find Skill by given id.
     *
     * @param id given id.
     * @return skill with given id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Skill> find(@PathVariable(value = "id") long id) {

        return new ResponseEntity<>(skillService.find(id), HttpStatus.OK);
    }

    /**
     * Find and return skills list.
     *
     * @return skill list.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Skill>> findAll() {

        return new ResponseEntity<>(skillService.findAll(), HttpStatus.OK);
    }

    /**
     * Update existing skill by given skill.
     *
     * @param updatedSkill given updated skill.
     * @return Skill object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Skill> updateSkill(@RequestBody Skill updatedSkill) {

        return new ResponseEntity<>(skillService.update(updatedSkill), HttpStatus.OK);
    }


    /**
     * Create new skill.
     *
     * @param skill given skill to persist
     * @return created skill.
     */
    @RequestMapping(value = "/skill", method = RequestMethod.POST)
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {

        return new ResponseEntity<>(skillService.create(skill), HttpStatus.CREATED);
    }
}
