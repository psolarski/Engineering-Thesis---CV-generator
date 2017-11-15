package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Skill find(Long id) {
        return skillRepository.findOne(id);
    }

    @Transactional
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    @Transactional
    public Skill create(Skill newSkill) {
        return skillRepository.saveAndFlush(newSkill);
    }

    @Transactional
    public Skill update(Skill updatedSkill) {

        Skill skill = skillRepository.findOne(updatedSkill.getId());

        skill.setLevel(updatedSkill.getLevel());
        skill.setName(updatedSkill.getName());
        skill.setDeveloper(updatedSkill.getDeveloper());

        return skillRepository.saveAndFlush(skill);
    }
}
