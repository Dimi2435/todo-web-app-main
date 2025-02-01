package ch.cern.todo.controller;

import ch.cern.todo.model.User;
import ch.cern.todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Dimitrios Milios
 */

/**
 * REST controller for managing User entities. Handles HTTP requests related to
 * users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController. Injects the UserService dependency.
     * 
     * @param userService The UserService to delegate user management tasks to.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves a list of all users.
     * 
     * @return A ResponseEntity containing a list of User objects and an HTTP status
     *         code.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id The ID of the user to retrieve.
     * @return A ResponseEntity containing the User object if found, or a 404 Not
     *         Found response if not.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Creates a new user.
     * 
     * @param user The User object to create (sent as JSON in the request body).
     * @return A ResponseEntity containing the created User object and an HTTP 201
     *         Created status code.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Updates an existing user.
     * 
     * @param id          The ID of the user to update.
     * @param updatedUser The updated User data (sent as JSON in the request body).
     * @return A ResponseEntity containing the updated User object if found, or a
     *         404 Not Found response if not.
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> updated = userService.updateUser(id, updatedUser);
        return updated.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id The ID of the user to delete.
     * @return A ResponseEntity with an HTTP 204 No Content status code.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
