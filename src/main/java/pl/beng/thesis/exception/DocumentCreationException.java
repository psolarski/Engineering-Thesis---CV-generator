package pl.beng.thesis.exception;

public class DocumentCreationException extends RuntimeException {

    public DocumentCreationException() {
        super();
    }

    public DocumentCreationException(String message) {

    }

    public DocumentCreationException(Throwable throwable) {
        super(throwable);
    }

    public DocumentCreationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
