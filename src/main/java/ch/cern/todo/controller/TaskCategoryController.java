package ch.cern.todo.controller;

import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.service.TaskCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ch.cern.todo.exception.ResourceNotFoundException;

import java.util.List;

/**
 * @author Dimitrios Milios
 */

/**
 * REST controller for managing TaskCategory entities. Handles HTTP requests
 * related to task categories.
 */
@RestController
@RequestMapping("/api/categories")
public class TaskCategoryController {
    private final TaskCategoryService taskCategoryService;

    public TaskCategoryController(TaskCategoryService taskCategoryService) {
        this.taskCategoryService = taskCategoryService;
    }

    /**
     * Creates a new task category.
     * 
     * @param category The TaskCategory object to create (sent as JSON in the
     *                 request body).
     * @return ResponseEntity containing the created TaskCategory entity.
     */
    @PostMapping
    public ResponseEntity<TaskCategory> createCategory(@RequestBody TaskCategory category) {
        TaskCategory createdCategory = taskCategoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    /**
     * Updates an existing task category.
     * 
     * @param id       The ID of the TaskCategory to update.
     * @param category The updated TaskCategory data (sent as JSON in the request
     *                 body).
     * @return ResponseEntity containing the updated TaskCategory entity.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskCategory> updateCategory(@PathVariable Long id, @RequestBody TaskCategory category) {
        TaskCategory updatedCategory = taskCategoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Deletes a task category by its ID.
     * 
     * @param id The ID of the TaskCategory to delete.
     * @return ResponseEntity with no content (HTTP 204 No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        taskCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a list of all task categories.
     * 
     * @return ResponseEntity containing a list of all TaskCategory entities.
     */
    @GetMapping
    public ResponseEntity<List<TaskCategory>> getAllCategories() {
        List<TaskCategory> categories = taskCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves a task category by its ID.
     * 
     * @param id The ID of the TaskCategory to retrieve.
     * @return ResponseEntity containing the TaskCategory if found; otherwise, a 404
     *         Not Found response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskCategory> getCategoryById(@PathVariable Long id) {
        return taskCategoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

    }
}