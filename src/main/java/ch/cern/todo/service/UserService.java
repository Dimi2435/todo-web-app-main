package ch.cern.todo.service;

import ch.cern.todo.config.SecurityConfig;
import ch.cern.todo.model.User;
import ch.cern.todo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Dimitrios Milios
 */

/**
 * Service layer for managing User entities. Provides business logic and data
 * access
 * operations related to users. This service handles user creation, retrieval,
 * update, and deletion. It also includes password encoding logic using
 * Spring Security's PasswordEncoder.
 */
@Service
@Transactional
public class UserService { // Removed @Transactional - manage transactions explicitly

    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    /**
     * Constructor for UserService. Injects the UserRepository and SecurityConfig
     * dependencies.
     * 
     * @param userRepository The repository used for data access operations.
     * @param securityConfig Provides access to the PasswordEncoder bean.
     */
    public UserService(UserRepository userRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        // this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retrieves a list of all users.
     * 
     * @return A list of all User objects.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the User if found, or an empty Optional if
     *         not.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Creates a new user. Encodes the password before saving.
     * 
     * @param user The User object to create.
     * @return The created User object.
     */
    public User createUser(User user) {
        // Encode the password before saving
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    /**
     * Updates an existing user. Encodes the password before saving.
     * 
     * @param id          The ID of the user to update.
     * @param updatedUser The updated User data.
     * @return An Optional containing the updated User if found, or an empty
     *         Optional if not.
     */
    public Optional<User> updateUser(Long id, User updatedUser) {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        return userRepository.findById(id).map(user -> {
            // Encode the password before saving. This could be skipped if the password does
            // not change
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setRoleType(updatedUser.getRoleType());
            return userRepository.save(user);
        });
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id The ID of the user to delete.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
