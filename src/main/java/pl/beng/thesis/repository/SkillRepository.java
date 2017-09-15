package pl.beng.thesis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.beng.thesis.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
}
