package pl.beng.thesis.service;

import com.lowagie.text.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.*;
import pl.beng.thesis.model.Developer;
import pl.beng.thesis.model.Education;
import pl.beng.thesis.model.Project;
import pl.beng.thesis.model.Skill;
import pl.beng.thesis.repository.DeveloperRepository;
import pl.beng.thesis.repository.EducationRepository;
import pl.beng.thesis.repository.ProjectRepository;
import pl.beng.thesis.repository.SkillRepository;
import pl.beng.thesis.util.PdfGeneratorUtil;

import java.util.List;

@Service
public class DeveloperService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TemplateEngine templateEngine;
    private final DeveloperRepository developerRepository;
    private final PdfGeneratorUtil pdfGeneratorUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EducationRepository educationRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    @Autowired
    public DeveloperService(TemplateEngine templateEngine,
                            DeveloperRepository developerRepository,
                            PdfGeneratorUtil pdfGeneratorUtil,
                            BCryptPasswordEncoder bCryptPasswordEncoder,
                            EducationRepository educationRepository, ProjectRepository projectRepository, SkillRepository skillRepository) {

        this.templateEngine = templateEngine;
        this.developerRepository = developerRepository;
        this.pdfGeneratorUtil = pdfGeneratorUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.educationRepository = educationRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }

    /**
     * Generate developer CV from Thymeleaf template
     *
     * @param username of developer
     */
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public byte[] generateDeveloperCv(String username) throws DocumentException {

        /* Find developer from database */
        Developer developer = developerRepository.findByUsername(username);

        /* Create Context */
        Context context = new Context();

        /* Set Context variables */
        context.setVariable("developerName", developer.getName());
        context.setVariable("developerSurname", developer.getSurname());
        context.setVariable("developerAddress", developer.getAddress());
        context.setVariable("developerEmail", developer.getEmail());
        context.setVariable("developerPhone", developer.getPhone());
        context.setVariable("developerEducation", developer.getEducations());
        context.setVariable("developerProjects", developer.getProjects());
        context.setVariable("developerSkills", developer.getSkills());

        /* Process html template */
        String processedHtml = templateEngine.process("developerCv.html", context);

        /* Generate and return pdf as byte array */
        return pdfGeneratorUtil.createPdf(processedHtml);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Developer find(Long id) {
        return developerRepository.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public List<Developer> findAll() {
        return developerRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = false)
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN') " +
            "OR (hasRole('ROLE_DEV') AND #updatedEmployee.username == principal)")
    public void updateDeveloper(Developer updatedEmployee) {
        developerRepository.saveAndFlush(updatedEmployee);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Developer findByEmail(String email) {
        return developerRepository.findByEmail(email);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED, readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_DEV', 'ROLE_ADMIN', 'ROLE_HR')")
    public Developer findByUsername(String username) {
        return developerRepository.findByUsername(username);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_HR')")
    public Developer createDeveloper(Developer newDeveloper) {
        logger.info("CREATING NEW DEVELOPER");
        newDeveloper.setPassword(bCryptPasswordEncoder.encode(newDeveloper.getPassword()));
        return developerRepository.saveAndFlush(newDeveloper);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN') " +
            "OR (hasRole('ROLE_DEV') AND #username == principal)")
    public Developer addNewSkill(String username, Education newEducation) {

        //Retrieve developer from database
        Developer developer = this.developerRepository.findByUsername(username);

        //Add new Education to list
        newEducation.setDeveloper(developer);
        developer.getEducations().add(newEducation);

        //Persist
        educationRepository.saveAndFlush(newEducation);
        return developerRepository.saveAndFlush(developer);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN') " +
            "OR (hasRole('ROLE_DEV') AND #username == principal)")
    public Developer addNewProject(String username, Project newProject) {

        //Retrieve developer from database
        Developer developer = this.developerRepository.findByUsername(username);

        //Add new Project to list
        newProject.setDeveloper(developer);
        developer.getProjects().add(newProject);

        //Persist
        this.projectRepository.saveAndFlush(newProject);
        return this.developerRepository.saveAndFlush(developer);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN') " +
            "OR (hasRole('ROLE_DEV') AND #username == principal)")
    public Developer addNewSkills(String username, List<Skill> skills) {

        //Retrieve developer from database
        Developer developer = this.developerRepository.findByUsername(username);

        //Add new skills from list
        developer.getSkills().addAll(skills);
        skills.forEach(skill -> skill.setDeveloper(developer));

        //Persist
        skills.forEach(this.skillRepository::saveAndFlush);
        return this.developerRepository.saveAndFlush(developer);
    }
}