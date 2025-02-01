package ch.cern.todo.exception;

/**
 * Custom exception class to indicate that a requested resource was not found.
 * This exception extends RuntimeException, meaning it doesn't require explicit
 * handling in the calling code;
 * however, global exception handlers can catch it to return appropriate HTTP
 * error responses (e.g., 404 Not Found).
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * Constructs a new ResourceNotFoundException with the specified message.
     * 
     * @param message The detail message explaining why the resource was not found.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}