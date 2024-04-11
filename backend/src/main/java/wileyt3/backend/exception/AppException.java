package wileyt3.backend.exception;

import org.springframework.http.HttpStatus;
/**
 * Custom exception class to include an HTTP status code along with the exception message.
 */
public class AppException extends RuntimeException {

    private final HttpStatus status;

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}