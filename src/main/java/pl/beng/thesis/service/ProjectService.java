package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import pl.beng.thesis.model.Project;
import pl.beng.thesis.repository.ProjectRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Project find(Long id) {
        return projectRepository.findOne(id);

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Project create(Project newProject) {
        return projectRepository.saveAndFlush(newProject);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN') " +
            "OR (hasRole('ROLE_DEV') AND #updatedProject.developer.username == principal)")
    public Project update(Project updatedProject) {

        Project project = projectRepository.findOne(updatedProject.getId());

        project.setCity(updatedProject.getCity());
        project.setCompany(updatedProject.getCompany());
        project.setDescription(updatedProject.getDescription());
        project.setEndDate(updatedProject.getEndDate());
        project.setPosition(updatedProject.getPosition());
        project.setStartDate(updatedProject.getStartDate());
        project.setDeveloper(updatedProject.getDeveloper());

        return projectRepository.saveAndFlush(project);
    }
}
