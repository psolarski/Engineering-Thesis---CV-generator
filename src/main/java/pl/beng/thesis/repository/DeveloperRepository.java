package pl.beng.thesis.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.beng.thesis.model.Developer;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {

    Developer findByUsername(String username);

    Developer findByEmail(String email);

    @EntityGraph("graph.developer")
    List<Developer> findAll();
}
