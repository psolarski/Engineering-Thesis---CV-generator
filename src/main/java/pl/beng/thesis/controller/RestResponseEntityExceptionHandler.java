package pl.beng.thesis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = { DocumentCreationException.class, TemplateEngineException.class })
    public ResponseEntity<Object> handleThymeleafAndDocumentException(RuntimeException ex, WebRequest request) {

        logger.error("Document Exception occurred: " + ex);
        String error_message = "document_exception";
        return handleExceptionInternal(ex, error_message,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = { RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {

        logger.error("Runtime Exception occurred: " + ex);
        String error_message = "runtime_exception";
        return handleExceptionInternal(ex, error_message,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {

        logger.error("Access Denied Exception occurred: " + ex);
        String error_message = "access_denied";
        return handleExceptionInternal(ex, error_message,
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = { OptimisticLockException.class})
    public ResponseEntity<Object> handleOptimisticLockException(OptimisticLockException ex, WebRequest request) {

        logger.error("Optimistic Lock Exception occurred: " + ex);
        String error_message = "optimistic_lock";
        return handleExceptionInternal(ex, error_message,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
