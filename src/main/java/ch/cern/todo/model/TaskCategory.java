package ch.cern.todo.model;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity class representing a TaskCategory in the system.
 */
@Entity
@Table(name = "TASK_CATEGORIES")
public class TaskCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(name = "NAME", nullable = false, unique = false)
    private String name;

    @Column(name = "DESCRIPTION", length = 255, nullable = false)
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    @JsonIgnore //This annotation prevents the tasks collection from being serialized along with TaskCategory objects.
    private Set<Task> tasks = new HashSet<>();

    // Default constructor
    public TaskCategory() {}

    // Constructor with parameters
    public TaskCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    // Method to add a task to the category
    public void addTask(Task task) {
        tasks.add(task);
        task.setCategory(this); // Set the category for the task
    }

    // Method to remove a task from the category
    public void removeTask(Task task) {
        tasks.remove(task);
        task.setCategory(null); // Clear the category for the task
    }

    // Override toString, equals, and hashCode for better usability
    @Override
    public String toString() {
        return "TaskCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    // Override equals and hashCode for proper comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false; // null check and class comparison
        TaskCategory that = (TaskCategory) o;
        return id != null && id.equals(that.id); // Consider other fields for comparison if necessary. equals compares by ID which should be fine if the entities were fetched from the database
    }

    @Override
    public int hashCode() {
        return 31;
    }
}