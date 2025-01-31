package ch.cern.todo.repository;

import ch.cern.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing Task entities.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * Finds a list of Tasks by their name, ignoring case.
     *
     * @param name the name of the task
     * @return a list of Tasks containing the specified name
     */
    List<Task> findByNameContainingIgnoreCase(String name);
    
    /**
     * Finds a list of Tasks by their category ID.
     *
     * @param categoryId the ID of the category
     * @return a list of Tasks belonging to the specified category
     */
    List<Task> findByCategory_Id(Long categoryId);
    
    /**
     * Finds a list of Tasks with deadlines between the specified start and end dates.
     *
     * @param start the start date
     * @param end the end date
     * @return a list of Tasks with deadlines within the specified range
     */
    List<Task> findByDeadlineBetween(LocalDateTime start, LocalDateTime end);
    
    // Additional query methods can be defined here
}

