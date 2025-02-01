package ch.cern.todo.controller;

import ch.cern.todo.dto.TaskDTO;
import ch.cern.todo.exception.ResourceNotFoundException;
import ch.cern.todo.model.Task;
import ch.cern.todo.model.User;
import ch.cern.todo.repository.UserRepository;
import ch.cern.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dimitrios Milios
 */

/**
 * REST controller for managing Task entities. Handles HTTP requests related to
 * tasks.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a list of all tasks.
     * 
     * @return A list of all Task entities.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Creates a new task.
     * 
     * @param task The Task object to create (sent as JSON in the request body).
     * @return The created Task entity.
     */
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    /**
     * Updates an existing task.
     * 
     * @param id   The ID of the task to update.
     * @param task The updated Task data (sent as JSON in the request body).
     * @return The updated Task entity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Deletes a task by its ID.
     * 
     * @param id The ID of the task to delete.
     * @return ResponseEntity with no content (HTTP 204 No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a task by its ID.
     * 
     * @param id The ID of the task to retrieve.
     * @return ResponseEntity containing the TaskDTO if found; otherwise, a 404 Not
     *         Found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Searches for tasks based on provided criteria.
     * 
     * @param name        The task name (optional).
     * @param description The task description (optional).
     * @param deadlineStr The task deadline (yyyy-MM-dd format, optional).
     * @param categoryId  The ID of the task category (optional).
     * @param userId      The ID of the task's assigned user (optional).
     * @return A list of TaskDTOs that match the search criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<List<TaskDTO>> searchTasks(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String deadlineStr,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long userId) {

        @SuppressWarnings("unused")
        User user = null;
        if (userId != null) { // check for valid userId input
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
        }

        List<TaskDTO> tasks = taskService.searchTasks(name, description, deadlineStr, categoryId, userId);
        return ResponseEntity.ok(tasks);
    }

}