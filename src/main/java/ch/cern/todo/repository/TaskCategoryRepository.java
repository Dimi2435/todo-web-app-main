package ch.cern.todo.repository;

import ch.cern.todo.model.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Dimitrios Milios
 */

/**
 * Repository interface for managing TaskCategory entities. This interface
 * extends JpaRepository,
 * providing base CRUD (Create, Read, Update, Delete) operations and allowing
 * for custom query methods.
 */
@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {

    /**
     * Finds a TaskCategory by its name. This method leverages Spring Data JPA's
     * query derivation capabilities
     * to automatically generate the necessary JPA query based on the method name.
     * 
     * @param name The name of the TaskCategory to search for.
     * @return An Optional containing the TaskCategory if found, or an empty
     *         Optional if not.
     */
    Optional<TaskCategory> findByName(String name);

}
