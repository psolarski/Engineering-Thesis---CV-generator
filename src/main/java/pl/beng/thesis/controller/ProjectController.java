package pl.beng.thesis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.beng.thesis.model.Project;
import pl.beng.thesis.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping(value = "projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * Find Project by given id.
     *
     * @param id given id.
     * @return Project with given id.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Project> find(@PathVariable(value = "id") long id) {

        return new ResponseEntity<>(projectService.find(id), HttpStatus.OK);
    }

    /**
     * Find and return Projects list.
     *
     * @return Project list.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> findAll() {

        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }

    /**
     * Update existing Project by given Project.
     *
     * @param updatedProject given updated Project.
     * @return Project object.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Project> updateProject(@RequestBody Project updatedProject) {

        return new ResponseEntity<>(projectService.update(updatedProject), HttpStatus.OK);
    }


    /**
     * Create new Project.
     *
     * @param project given Project to persist
     * @return created Project.
     */
    @RequestMapping(value = "/Project", method = RequestMethod.POST)
    public ResponseEntity<Project> createProject(@RequestBody Project project) {

        return new ResponseEntity<>(projectService.create(project), HttpStatus.CREATED);
    }
}