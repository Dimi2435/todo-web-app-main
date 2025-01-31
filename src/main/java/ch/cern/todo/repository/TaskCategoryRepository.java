package ch.cern.todo.repository;

import ch.cern.todo.model.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing TaskCategory entities.
 */
@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Long> {
    
    /**
     * Finds a TaskCategory by its name.
     *
     * @param name the name of the task category
     * @return an Optional containing the TaskCategory if found, or empty if not
     */
    Optional<TaskCategory> findByName(String name);
    
    // Additional query methods can be defined here
}

