package ch.cern.todo.service;

import ch.cern.todo.model.Task;
import ch.cern.todo.dto.TaskDTO;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskRepository;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ch.cern.todo.exception.TodoNotFoundException;
import ch.cern.todo.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Task entities.
 */
@Service
@Transactional
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Creates a new Task.
     *
     * @param task the Task to create
     * @return the created Task
     */
    public Task createTask(Task task) {
        validate(task);
        return taskRepository.save(task);
    }

    /**
     * Updates an existing Task.
     *
     * @param id   the ID of the Task to update
     * @param task the updated Task data
     * @return the updated Task
     */
    public Task updateTask(Long id, Task task) {
        validate(task);
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setName(task.getName());
                    existingTask.setDescription(task.getDescription());
                    existingTask.setDeadline(task.getDeadline());
                    existingTask.setCategory(task.getCategory());
                    return taskRepository.save(existingTask);
                })
                .orElseThrow(() -> new TodoNotFoundException("Task not found with ID: " + id));
    }

    /**
     * Deletes a Task by its ID.
     *
     * @param id the ID of the Task to delete
     */
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TodoNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    // /**
    // * Searches for Tasks based on various criteria.
    // *
    // * @param name the name of the task
    // * @param description the description of the task
    // * @param deadline the deadline of the task
    // * @return a list of matching Tasks
    // */
    // public List<Task> searchTasks(String name, String description, LocalDateTime
    // deadline, TaskCategory category) {
    // // Use a more targeted query if available in the JPA repository
    // List<Task> tasks = taskRepository.findAll();
    // return tasks.stream().filter(task -> task.matches(name, description,
    // deadline, category)).toList();
    // }

    public List<Task> searchTasks(String name, String description, LocalDateTime deadline, Long categoryId) {
        return taskRepository.searchTasks(name, description, deadline, categoryId); // Use the repository's search
                                                                                    // method
    }

    /**
     * Validates the Task data.
     *
     * @param task the Task to validate
     */
    private void validate(Task task) {
        if (!StringUtils.hasText(task.getName())) {
            throw new IllegalArgumentException("Task name must be provided");
        }
        if (task.getName().length() > 100) {
            throw new IllegalArgumentException("Task name cannot exceed 100 characters");
        }
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    TaskDTO dto = new TaskDTO();
                    dto.setId(task.getId());
                    dto.setName(task.getName());
                    dto.setDescription(task.getDescription());
                    dto.setDeadline(task.getDeadline());
                    if (task.getCategory() != null) {
                        dto.setCategoryName(task.getCategory().getName());
                    }
                    return dto;
                });
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Retrieve all tasks from the repository
    }

}