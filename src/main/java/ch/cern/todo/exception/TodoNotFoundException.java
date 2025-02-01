package ch.cern.todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class to indicate that a Todo item was not found.
 * This exception is annotated with @ResponseStatus(HttpStatus.NOT_FOUND), which
 * means that Spring will automatically
 * return an HTTP 404 Not Found response when this exception is thrown. This
 * simplifies exception handling
 * and ensures consistent error responses to clients.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(String message) {
        super(message);
    }
}