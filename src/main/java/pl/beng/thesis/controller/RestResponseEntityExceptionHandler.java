package pl.beng.thesis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.thymeleaf.exceptions.TemplateEngineException;
import pl.beng.thesis.exception.DocumentCreationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = { DocumentCreationException.class, TemplateEngineException.class })
    public ResponseEntity<Object> handleThymeleafAndDocumentException(RuntimeException ex, WebRequest request) {

        logger.error("Document Exception occurred: " + ex);
        String bodyOfResponse = "Unable to create document!";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {

        logger.error("Runtime Exception occurred: " + ex);
        String bodyOfResponse = "Upsss, something went wrong!";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
