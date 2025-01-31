package ch.cern.todo.controller;

import ch.cern.todo.model.Task;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller class for managing Task entities.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Retrieves all Tasks.
     *
     * @return ResponseEntity containing a list of all Tasks
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Creates a new Task.
     *
     * @param task the Task to create
     * @return ResponseEntity containing the created Task
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')") // Only users with the 'USER' role can create tasks
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    /**
     * Updates an existing Task.
     *
     * @param id   the ID of the Task to update
     * @param task the updated Task data
     * @return ResponseEntity containing the updated Task
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Deletes a Task by its ID.
     *
     * @param id the ID of the Task to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a Task by its ID.
     *
     * @param id the ID of the Task
     * @return ResponseEntity containing the Task if found, or not found status
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') and #task.user.id == authentication.principal.id  or hasRole('ADMIN')") // Task owner or Admin
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    // Additional endpoints can be added here
    @GetMapping("/search") // Improved search endpoint
    public List<Task> searchTasks(
           @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "deadline", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline,
            @RequestParam(value = "categoryId", required = false) Long categoryId)     
            {  // Search by category ID
            TaskCategory category = categoryId != null ? new TaskCategory() {{ setId(categoryId); }} : null;
            return taskService.searchTasks(name, description, deadline, categoryId); // Use the optimized method in TaskService
    }
}