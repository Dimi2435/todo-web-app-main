package ch.cern.todo.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a Task in the system.
 */
@Entity
@Table(name = "TASKS")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private Long id;

    @Column(name = "TASK_NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "TASK_DESCRIPTION", length = 255, nullable = false)
    private String description;

    @Column(name = "DEADLINE", nullable = false)
    private LocalDateTime deadline;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private TaskCategory category;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    // Default constructor
    public Task() {}

    // Constructor with parameters
    public Task(String name, String description, LocalDateTime deadline, TaskCategory category, User user) {
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.category = category;
        this.user = user;
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

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public void setCategory(TaskCategory category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Override toString, equals, and hashCode for better usability
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                '}';
    }

    // Override equals and hashCode for proper comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return id != null && id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}