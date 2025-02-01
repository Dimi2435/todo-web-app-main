package ch.cern.todo.dto;

import java.time.LocalDateTime;

/**
 * @author Dimitrios Milios
 */

/**
 * Data Transfer Object (DTO) for representing Task data. Used for transferring
 * task information between layers
 * (e.g., service layer to controller layer) and for data exchange with clients
 * (e.g., REST API responses). This
 * class avoids including Hibernate proxies or unnecessary data.
 */
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime deadline;
    private String categoryName; // Represents the name of the associated category
    private String userName; // Represents the username of the assigned user

    /**
     * Getter for the task ID.
     * 
     * @return The ID of the task.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the task ID.
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
    // Getter and Setter for deadline
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
     * Getter for the category name associated with the task.
     * 
     * @return The name of the category.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Setter for the category name associated with the task.
     * 
     * @param categoryName The category name to set.
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Getter for the username of the user assigned to the task.
     * 
     * @return The username of the assigned user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for the username of the user assigned to the task.
     * 
     * @param userName The username to set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}