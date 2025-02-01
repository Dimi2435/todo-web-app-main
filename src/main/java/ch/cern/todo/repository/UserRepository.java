package ch.cern.todo.repository;

import ch.cern.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Dimitrios Milios
 */

/**
 * Repository interface for managing User entities. This interface extends
 * JpaRepository,
 * providing base CRUD (Create, Read, Update, Delete) operations and allowing
 * for custom query methods.
 * Spring Data JPA automatically implements the necessary methods based on the
 * JpaRepository interface.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}