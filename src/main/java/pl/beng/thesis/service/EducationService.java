package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.beng.thesis.model.Education;
import pl.beng.thesis.repository.EducationRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Transactional
    public Education create(Education newEducation) {
        return educationRepository.saveAndFlush(newEducation);
    }

    @Transactional
    public Education find(Long id) {
        return educationRepository.findOne(id);
    }

    @Transactional
    public List<Education> findAll() {
        return educationRepository.findAll();
    }

    @Transactional
    public Education update(Education updatedEducation) {

        Education education = educationRepository.findOne(updatedEducation.getId());

        education.setEndDate(updatedEducation.getEndDate());
        education.setStartDate(updatedEducation.getStartDate());
        education.setFaculty(updatedEducation.getFaculty());
        education.setSpecialization(updatedEducation.getSpecialization());
        education.setUniversity(updatedEducation.getUniversity());
        education.setDeveloper(updatedEducation.getDeveloper());

        return educationRepository.saveAndFlush(education);
    }
}
