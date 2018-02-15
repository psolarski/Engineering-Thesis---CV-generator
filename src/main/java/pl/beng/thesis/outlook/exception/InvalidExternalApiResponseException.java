package pl.beng.thesis.outlook.exception;

public class InvalidExternalApiResponseException extends Exception {

    public InvalidExternalApiResponseException() {
        super();
    }

    public InvalidExternalApiResponseException(String message) {

    }

    public InvalidExternalApiResponseException(Throwable throwable) {
        super(throwable);
    }

    public InvalidExternalApiResponseException(String message, Throwable throwable) {
        super(message, throwable);
    }
}