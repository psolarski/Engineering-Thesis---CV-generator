package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pl.beng.thesis.model.Skill;
import pl.beng.thesis.repository.SkillRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;


    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Skill find(Long id) {
        return skillRepository.findOne(id);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Skill create(Skill newSkill) {
        return skillRepository.saveAndFlush(newSkill);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN') " +
            "OR (hasRole('ROLE_DEV') AND #updatedSkill.developer.username == principal)")
    public Skill update(Skill updatedSkill) {

        Skill skill = skillRepository.findOne(updatedSkill.getId());

        skill.setLevel(updatedSkill.getLevel());
        skill.setName(updatedSkill.getName());
        skill.setDeveloper(updatedSkill.getDeveloper());

        return skillRepository.saveAndFlush(skill);
    }
}
