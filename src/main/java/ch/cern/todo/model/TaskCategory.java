package ch.cern.todo.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Dimitrios Milios
 */

/**
 * Entity class representing a TaskCategory in the system. Maps to the
 * 'TASK_CATEGORIES' database table.
 */
@Entity
@Table(name = "TASK_CATEGORIES")
public class TaskCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "DESCRIPTION", length = 255, nullable = false)
    private String description;

    /**
     * This is a bidirectional relationship. Using FetchType.EAGER to fetch tasks
     * eagerly (all tasks of this category will be loaded at the same time)
     * The @JsonIgnore annotation prevents the tasks collection from being
     * serialized into JSON, which is important to avoid issues with circular
     * references and infinite recursion when serializing the object into JSON
     * format.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    @JsonIgnore // This annotation prevents the tasks collection from being serialized along
                // with TaskCategory objects.
    private Set<Task> tasks = new HashSet<>();

    /**
     * No-argument constructor required by JPA/Hibernate.
     */
    public TaskCategory() {
    }

    /**
     * Constructor to create a TaskCategory object.
     * 
     * @param name        The name of the category.
     * @param description The description of the category.
     */
    public TaskCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Getter for the category ID.
     * 
     * @return The ID of the category.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the category ID. Used by JPA/Hibernate.
     * 
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the category name.
     * 
     * @return The name of the category.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the category name.
     * 
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the category description.
     * 
     * @return The description of the category.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the category description.
     * 
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the set of tasks associated with this category.
     * 
     * @return The set of tasks.
     */
    public Set<Task> getTasks() {
        return tasks;
    }

    /**
     * Setter for the set of tasks associated with this category. Used by
     * JPA/Hibernate.
     * 
     * @param tasks The set of tasks to set.
     */
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the category's set of tasks. Updates the task's category
     * reference as well.
     * 
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
        task.setCategory(this); // Set the category for the task
    }

    /**
     * Removes a task from the category's set of tasks. Updates the task's category
     * reference as well.
     * 
     * @param task The task to remove.
     */
    public void removeTask(Task task) {
        tasks.remove(task);
        task.setCategory(null); // Clear the category for the task
    }

    /**
     * Provides a string representation of the TaskCategory object. Useful for
     * logging and debugging.
     * 
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "TaskCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    /**
     * Compares this TaskCategory object to another object for equality. Uses ID for
     * comparison.
     * 
     * @param o The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false; // null check and class comparison
        TaskCategory that = (TaskCategory) o;
        return id != null && id.equals(that.id); // Consider other fields for comparison if necessary. equals compares
                                                 // by ID which should be fine if the entities were fetched from the
                                                 // database
    }

    /**
     * Returns a hash code for this TaskCategory object. Based on the ID.
     * 
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return 31;
    }
}