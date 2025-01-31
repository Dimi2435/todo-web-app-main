package ch.cern.todo.repository;

import ch.cern.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

     @Query("SELECT t FROM Task t " +
           "WHERE (:name IS NULL OR t.name LIKE %:name%) " +
           "AND (:description IS NULL OR t.description LIKE %:description%) " +
           "AND (:deadline IS NULL OR t.deadline = :deadline) " +
           "AND (:category IS NULL OR t.category.id = :categoryId)") // Use explicit join and category ID
    List<Task> searchTasks(
            @Param("name") String name,
            @Param("description") String description,
            @Param("deadline") LocalDateTime deadline,
            @Param("categoryId") Long categoryId);
    
    // Additional query methods can be defined here
}

