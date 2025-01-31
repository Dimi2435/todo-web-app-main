package ch.cern.todo.controller;

import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.service.TaskCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ch.cern.todo.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Controller class for managing TaskCategory entities.
 */
@RestController
@RequestMapping("/api/categories")
public class TaskCategoryController {
    private final TaskCategoryService taskCategoryService;

    public TaskCategoryController(TaskCategoryService taskCategoryService) {
        this.taskCategoryService = taskCategoryService;
    }

    /**
     * Creates a new TaskCategory.
     *
     * @param category the TaskCategory to create
     * @return ResponseEntity containing the created TaskCategory
     */
    @PostMapping
    public ResponseEntity<TaskCategory> createCategory(@RequestBody TaskCategory category) {
        TaskCategory createdCategory = taskCategoryService.createCategory(category);
        return ResponseEntity.ok(createdCategory);
    }

    /**
     * Updates an existing TaskCategory.
     *
     * @param id       the ID of the TaskCategory to update
     * @param category the updated TaskCategory data
     * @return ResponseEntity containing the updated TaskCategory
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskCategory> updateCategory(@PathVariable Long id, @RequestBody TaskCategory category) {
        TaskCategory updatedCategory = taskCategoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Deletes a TaskCategory by its ID.
     *
     * @param id the ID of the TaskCategory to delete
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        taskCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves all TaskCategories.
     *
     * @return ResponseEntity containing a list of all TaskCategories
     */
    @GetMapping
    public ResponseEntity<List<TaskCategory>> getAllCategories() {
        List<TaskCategory> categories = taskCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves a TaskCategory by its ID.
     *
     * @param id the ID of the TaskCategory
     * @return ResponseEntity containing the TaskCategory if found, or not found status
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskCategory> getCategoryById(@PathVariable Long id) {
    	return taskCategoryService.getCategoryById(id)
    	        .map(ResponseEntity::ok)
    	        .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));

    }
} 