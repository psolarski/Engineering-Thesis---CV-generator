package pl.beng.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beng.thesis.model.Developer;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

}
