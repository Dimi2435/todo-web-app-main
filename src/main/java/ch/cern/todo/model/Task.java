package ch.cern.todo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Dimitrios Milios
 */

/**
 * Entity class representing a Task in the system. Maps to the 'TASKS' database
 * table.
 */
@Entity
@Table(name = "TASKS")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private Long id;

    @Column(name = "TASK_NAME", nullable = false, length = 100, unique = true)
    private String name;

    @Column(name = "TASK_DESCRIPTION", length = 255, nullable = false)
    private String description;

    @Column(name = "DEADLINE", nullable = false)
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY) // Optimize fetching
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private TaskCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    // @Column(name = "USER_ID", insertable = false, updatable = false) // Add index
    private User user;

    /**
     * Default constructor for JPA.
     */
    public Task() {
    }

    /**
     * Constructor for creating a Task object.
     * 
     * @param name        The name of the task.
     * @param description The description of the task.
     * @param deadline    The deadline for the task.
     * @param category    The category the task belongs to.
     * @param user        The user assigned to the task.
     */
    public Task(String name, String description, LocalDateTime deadline, TaskCategory category, User user) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.category = category;
        this.user = user;
    }

    /**
     * Getter for the task ID.
     * 
     * @return The ID of the task.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the task ID. Used primarily by JPA.
     * 
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the task name.
     * 
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the task name.
     * 
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the task description.
     * 
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the task description.
     * 
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the task deadline.
     * 
     * @return The deadline of the task.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Setter for the task deadline.
     * 
     * @param deadline The deadline to set.
     */
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * Getter for the task category.
     * 
     * @return The category object associated with this task.
     */
    public TaskCategory getCategory() {
        return category;
    }

    /**
     * Setter for the task category.
     * 
     * @param category The category object to associate with this task.
     */
    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    /**
     * Getter for the user assigned to the task.
     * 
     * @return The user object assigned to this task.
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the user assigned to the task.
     * 
     * @param user The user object to assign to this task.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Helper method for combined search functionality. Checks if the task matches
     * specified criteria.
     * 
     * @param name        The name to search for (can be null or empty).
     * @param description The description to search for (can be null or empty).
     * @param deadline    The deadline to search for (can be null).
     * @param category    The category to search for (can be null).
     * @param user        The user to search for (can be null).
     * @return True if the task matches all non-null criteria, false otherwise.
     */
    public boolean matches(String name, String description, LocalDateTime deadline, TaskCategory category, User user) {

        if (name != null && !this.name.contains(name)) {
            return false;
        }
        if (description != null && !this.description.contains(description)) {
            return false;
        }
        if (deadline != null && !this.deadline.isEqual(deadline)) {
            return false;
        }
        if (user != null && !this.user.equals(user)) {
            return false;
        }
        return category == null || this.category.equals(category);

    }

    /**
     * Returns a string representation of the Task object. Useful for logging and
     * debugging.
     * 
     * @return A string representation of the Task.
     */
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", user=" + user +
                '}';
    }

    /**
     * Compares this Task object to another object for equality. Uses ID for
     * comparison.
     * 
     * @param o The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Task))
            return false;
        Task task = (Task) o;
        return id != null && id.equals(task.id);
    }

    /**
     * Returns a hash code for this Task object. Based on the ID.
     * 
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return 31;
    }
}