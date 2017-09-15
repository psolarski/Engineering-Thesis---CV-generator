package pl.beng.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beng.thesis.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
