package pl.beng.thesis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.thymeleaf.exceptions.TemplateEngineException;
import pl.beng.thesis.exception.DocumentCreationException;

import javax.persistence.OptimisticLockException;
import java.util.Locale;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    public RestResponseEntityExceptionHandler(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    @ExceptionHandler(value = { DocumentCreationException.class, TemplateEngineException.class })
    public ResponseEntity<Object> handleThymeleafAndDocumentException(RuntimeException ex, WebRequest request,
                                                                      Locale locale) {

        logger.error("Document Exception occurred: " + ex);
        String error_message = resourceBundleMessageSource.getMessage("exception.document", null, locale);
        return handleExceptionInternal(ex, error_message,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request,
                                                         Locale locale) {

        logger.error("Runtime Exception occurred: " + ex);
        String error_message = resourceBundleMessageSource.getMessage("exception.runtime", null, locale);
        return handleExceptionInternal(ex, error_message,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request,
                                                              Locale locale) {

        logger.error("Access Denied Exception occurred: " + ex);
        String error_message = resourceBundleMessageSource.getMessage("exception.access_denied", null, locale);
        return handleExceptionInternal(ex, error_message,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = { OptimisticLockException.class})
    public ResponseEntity<Object> handleOptimisticLockException(OptimisticLockException ex, WebRequest request,
                                                                Locale locale) {

        logger.error("Optimistic Lock Exception occurred: " + ex);
        String error_message = resourceBundleMessageSource.getMessage("exception.optimistic_lock", null, locale);
        return handleExceptionInternal(ex, error_message,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
