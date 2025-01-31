package ch.cern.todo.repository;

import ch.cern.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a User by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the User if found, or empty if not
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Finds a User by their email.
     *
     * @param email the email of the user
     * @return an Optional containing the User if found, or empty if not
     */
    Optional<User> findByEmail(String email);
    
    // Additional query methods can be defined here
}