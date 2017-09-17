package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beng.thesis.model.Project;
import pl.beng.thesis.repository.ProjectRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;


    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public Project find(Long id) {
        return projectRepository.findOne(id);
    }

    @Transactional
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Transactional
    public Project create(Project newProject) {
        return projectRepository.saveAndFlush(newProject);
    }

    @Transactional
    public Project update(Project updatedProject) {
        return projectRepository.saveAndFlush(updatedProject);
    }
}
