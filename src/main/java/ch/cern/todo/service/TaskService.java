package ch.cern.todo.service;

import ch.cern.todo.model.Task;
import ch.cern.todo.dto.TaskDTO;
import ch.cern.todo.repository.TaskRepository;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ch.cern.todo.exception.TodoNotFoundException;
import org.slf4j.Logger;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Dimitrios Milios
 */

/**
 * Service class for managing Task entities. This service layer handles
 * business logic and data access operations related to tasks.
 */
@Service
@Transactional
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    /**
     * Constructor for TaskService. Injects the TaskRepository dependency.
     * 
     * @param taskRepository The repository used for data access operations.
     */
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Creates a new Task.
     * 
     * @param task The Task object to create.
     * @return The created Task object.
     * @throws IllegalArgumentException If the task data is invalid.
     */
    public Task createTask(Task task) {
        validate(task);
        return taskRepository.save(task);
    }

    /**
     * Updates an existing Task.
     * 
     * @param id   The ID of the Task to update.
     * @param task The updated Task data.
     * @return The updated Task object.
     * @throws TodoNotFoundException    If the task with the given ID is not found.
     * @throws IllegalArgumentException If the task data is invalid.
     */
    public Task updateTask(Long id, Task task) {
        validate(task);
        return taskRepository.findById(id)
                .map(existingTask -> {
                    existingTask.setName(task.getName());
                    existingTask.setDescription(task.getDescription());
                    existingTask.setDeadline(task.getDeadline());
                    existingTask.setCategory(task.getCategory());
                    existingTask.setUser(task.getUser());
                    return taskRepository.save(existingTask);
                })
                .orElseThrow(() -> new TodoNotFoundException("Task not found with ID: " + id));
    }

    /**
     * Deletes a Task by its ID.
     * 
     * @param id The ID of the Task to delete.
     * @throws TodoNotFoundException If the task with the given ID is not found.
     */
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new TodoNotFoundException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
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

    /**
     * Retrieves a Task by its ID and converts it to a TaskDTO.
     * 
     * @param id The ID of the Task to retrieve.
     * @return An Optional containing the TaskDTO if found, or an empty Optional if
     *         not.
     */
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
                    if (task.getUser() != null) {
                        dto.setUserName(task.getUser().getUsername());
                    }
                    return dto;
                });
    }

    /**
     * Retrieves all Tasks.
     * 
     * @return A list of all Task objects.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Retrieve all tasks from the repository
    }

    /**
     * Searches for Tasks based on provided criteria.
     * 
     * @param name        The task name (optional).
     * @param description The task description (optional).
     * @param deadlineStr The task deadline (yyyy-MM-dd format, optional).
     * @param categoryId  The ID of the task category (optional).
     * @param userId      The ID of the task's assigned user (optional).
     * @return A list of TaskDTOs that match the search criteria.
     */
    public List<TaskDTO> searchTasks(String name, String description, String deadlineStr, Long categoryId,
            Long userId) {
        List<Task> tasks = taskRepository.findAll((Specification<Task>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"));
            }

            if (StringUtils.hasText(description)) {
                predicates.add(cb.like(
                        cb.lower(root.get("description")),
                        "%" + description.toLowerCase() + "%"));
            }

            LocalDateTime deadline = null;
            try {
                if (deadlineStr != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Customize the pattern if
                                                                                             // needed
                    deadline = LocalDateTime.parse(deadlineStr, formatter);
                }
            } catch (DateTimeParseException e) {
                logger.error("Invalid deadline format: {}", deadlineStr, e);
                // Consider throwing an exception instead of returning an empty list
                return cb.conjunction();
            }

            if (deadline != null) {
                predicates.add(cb.equal(root.get("deadline"), deadline)); // Corrected predicate for deadline
            }

            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }

            if (userId != null) {
                predicates.add(cb.equal(root.get("user").get("id"), userId));
            }

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        });

        return tasks.stream()
                .map(this::mapToTaskDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps a Task entity to a TaskDTO.
     * 
     * @param task The Task entity to map.
     * @return A TaskDTO object.
     */
    private TaskDTO mapToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDeadline(task.getDeadline());
        if (task.getCategory() != null) {
            taskDTO.setCategoryName(task.getCategory().getName());
        }
        if (task.getUser() != null) {
            taskDTO.setUserName(task.getUser().getUsername());
        }
        return taskDTO;
    }

}