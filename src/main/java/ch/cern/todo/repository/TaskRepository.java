package ch.cern.todo.repository;

import ch.cern.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dimitrios Milios
 */

/**
 * Repository interface for managing Task entities. Extends JpaRepository for
 * basic CRUD operations
 * and JpaSpecificationExecutor for more complex, criteria-based queries.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    /**
     * Finds Tasks whose names contain the given string (case-insensitive).
     * Uses Spring Data JPA's query derivation to create the query automatically.
     * 
     * @param name The substring to search for in task names.
     * @return A list of Tasks whose names contain the given substring
     *         (case-insensitive).
     */
    List<Task> findByNameContainingIgnoreCase(String name);

    /**
     * Finds Tasks associated with a specific category ID.
     * Uses Spring Data JPA's query derivation to create the query automatically.
     * 
     * @param categoryId The ID of the category to search for.
     * @return A list of Tasks associated with the given category ID.
     */
    List<Task> findByCategory_Id(Long categoryId);

    /**
     * Finds Tasks with deadlines falling within a specified date range (inclusive).
     * Uses Spring Data JPA's query derivation to create the query automatically.
     * 
     * @param start The start date of the range (inclusive).
     * @param end   The end date of the range (inclusive).
     * @return A list of Tasks with deadlines within the specified range.
     */
    List<Task> findByDeadlineBetween(LocalDateTime start, LocalDateTime end);

}
