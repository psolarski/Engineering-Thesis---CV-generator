package pl.beng.thesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import pl.beng.thesis.model.Education;
import pl.beng.thesis.repository.EducationRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_DEV')")
    public Education create(Education newEducation) {
        return educationRepository.saveAndFlush(newEducation);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Education find(Long id) {
        return educationRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public List<Education> findAll() {
        return educationRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
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
