package ch.cern.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling exceptions thrown in the application.
 * This class uses @ControllerAdvice to globally handle exceptions across all
 * controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ResourceNotFoundException. This exception is typically thrown when a
     * requested resource (e.g., Task, User, Category)
     * is not found in the database.
     * 
     * @param ex The ResourceNotFoundException that occurred.
     * @return A ResponseEntity with HTTP status 404 (Not Found) and the exception
     *         message as the response body.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles IllegalArgumentException. This exception is typically thrown when
     * invalid data is provided,
     * such as null or empty values where they are not allowed.
     * 
     * @param ex The IllegalArgumentException that occurred.
     * @return A ResponseEntity with HTTP status 400 (Bad Request) and the exception
     *         message as the response body.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles TodoNotFoundException. This exception is a custom exception which may
     * indicate that a Todo item was not found.
     * 
     * @param ex The TodoNotFoundException that occurred.
     * @return A ResponseEntity with HTTP status 404 (Not Found) and the exception
     *         message as the response body.
     */
    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<String> handleTodoNotFound(TodoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles any other exception that is not explicitly handled by other exception
     * handlers.
     * 
     * @param ex The exception that occurred.
     * @return A ResponseEntity with HTTP status 500 (Internal Server Error) and a
     *         generic error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
