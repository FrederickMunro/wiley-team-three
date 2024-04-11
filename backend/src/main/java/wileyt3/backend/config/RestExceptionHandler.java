package wileyt3.backend.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import wileyt3.backend.dto.ErrorDto;
import wileyt3.backend.exception.AppException;
/**
 * Global exception handler class that captures exceptions and transforms them into HTTP responses.
 * This is part of the controller advice which allows it to handle exceptions across all controllers.
 */
@ControllerAdvice
public class RestExceptionHandler {
    /**
     * Handles custom AppException by returning a structured JSON error response.
     * Maps AppException to HTTP response status codes and error information.
     *
     * @param ex the captured AppException instance containing error details.
     * @return A ResponseEntity containing the custom error structured as ErrorDto.
     */
    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorDto(ex.getMessage()));
    }
}