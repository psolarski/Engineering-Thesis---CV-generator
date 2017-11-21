package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ROLE_DEV')")
    public Education create(Education newEducation) {
        return educationRepository.saveAndFlush(newEducation);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Education find(Long id) {
        return educationRepository.findOne(id);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public List<Education> findAll() {
        return educationRepository.findAll();
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN') " +
            "OR (hasRole('ROLE_DEV') AND #updatedEducation.developer.username == principal)")
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
