package ch.cern.todo.service;

import ch.cern.todo.model.Task;
import ch.cern.todo.dto.TaskDTO;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskRepository;
import ch.cern.todo.repository.UserRepository;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ch.cern.todo.exception.TodoNotFoundException;
import ch.cern.todo.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                    existingTask.setUser(task.getUser());
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
                    if (task.getUser() != null) {
                        dto.setUserName(task.getUser().getUsername());
                    }
                    return dto;
                });
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll(); // Retrieve all tasks from the repository
    }

    public List<Task> searchTasks(String name, String description, LocalDateTime deadline, Long categoryId,
            Long userId) {
        return taskRepository.findAll((Specification<Task>) (root, query, cb) -> {
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

            if (deadline != null) {
                predicates.add(cb.equal(root.get("deadline"), deadline));
            }

            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }

            // ... existing code ...
            if (userId != null) {
                predicates.add(cb.equal(root.get("user").get("id"), userId));
            }
            // ... existing code ...

            return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
        });
    }

}