package pl.beng.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beng.thesis.model.Education;

public interface EducationRepository extends JpaRepository<Education, Integer> {
}
