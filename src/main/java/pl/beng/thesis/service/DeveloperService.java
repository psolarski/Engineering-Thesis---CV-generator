package pl.beng.thesis.service;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.*;
import org.thymeleaf.spring4.SpringTemplateEngine;
import pl.beng.thesis.model.Developer;
import pl.beng.thesis.repository.DeveloperRepository;
import pl.beng.thesis.util.PdfGeneratorUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class DeveloperService {

    private final TemplateEngine templateEngine;
    private final DeveloperRepository developerRepository;
    private final PdfGeneratorUtil pdfGeneratorUtil;

    @Autowired
    public DeveloperService(TemplateEngine templateEngine,
                            DeveloperRepository developerRepository,
                            PdfGeneratorUtil pdfGeneratorUtil) {

        this.templateEngine = templateEngine;
        this.developerRepository = developerRepository;
        this.pdfGeneratorUtil = pdfGeneratorUtil;
    }

    /**
     * Generate developer CV from Thymeleaf template
     *
     * @param id of developer
     */
    public byte[] generateDeveloperCv(Long id) throws DocumentException {

        /* Find developer from database */
        Developer developer = developerRepository.findOne(id);

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
}