package ch.cern.todo.service;

import ch.cern.todo.exception.ResourceNotFoundException;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Dimitrios Milios
 */

/**
 * Service layer for managing TaskCategory entities. Provides business logic and
 * data access
 * operations related to task categories. This service layer is responsible for
 * handling the
 * creation, retrieval, updating and deletion of TaskCategory entities.
 */
@Service
@Transactional
public class TaskCategoryService {
    private final TaskCategoryRepository taskCategoryRepository;

    /**
     * Constructor for TaskCategoryService. Injects the TaskCategoryRepository
     * dependency.
     * 
     * @param taskCategoryRepository The repository used for data access operations.
     */
    public TaskCategoryService(TaskCategoryRepository taskCategoryRepository) {
        this.taskCategoryRepository = taskCategoryRepository;
    }

    /**
     * Creates a new task category.
     * 
     * @param category The TaskCategory object to create.
     * @return The created TaskCategory object.
     * @throws IllegalArgumentException If the category name is invalid.
     */
    public TaskCategory createCategory(TaskCategory category) {
        validateCategory(category);
        return taskCategoryRepository.save(category);
    }

    /**
     * Retrieves a task category by its ID.
     * 
     * @param id The ID of the task category to retrieve.
     * @return An Optional containing the TaskCategory if found, or an empty
     *         Optional if not.
     */
    public Optional<TaskCategory> getCategoryById(Long id) {
        return taskCategoryRepository.findById(id);
    }

    /**
     * Retrieves all task categories.
     * 
     * @return A list of all TaskCategory objects.
     */
    public List<TaskCategory> getAllCategories() {
        return taskCategoryRepository.findAll();
    }

    /**
     * Updates an existing task category.
     * 
     * @param id       The ID of the task category to update.
     * @param category The updated TaskCategory data.
     * @return The updated TaskCategory object.
     * @throws ResourceNotFoundException If the task category with the given ID is
     *                                   not found.
     * @throws IllegalArgumentException  If the category name is invalid.
     */
    public TaskCategory updateCategory(Long id, TaskCategory category) {
        validateCategory(category);
        return taskCategoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    existingCategory.setDescription(category.getDescription());
                    return taskCategoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id));
    }

    /**
     * Deletes a task category by its ID.
     * 
     * @param id The ID of the task category to delete.
     * @throws IllegalArgumentException If the task category with the given ID is
     *                                  not found.
     */
    public void deleteCategory(Long id) {
        if (!taskCategoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found");
        }
        taskCategoryRepository.deleteById(id);
    }

    /**
     * Validates the TaskCategory data before saving it to ensure data integrity.
     * 
     * @param category The TaskCategory to validate.
     * @throws IllegalArgumentException If the category name is null or blank.
     */
    private void validateCategory(TaskCategory category) {
        if (category.getName() == null || category.getName().isBlank()) { // Use isBlank to check for empty or
                                                                          // whitespace-only names
            throw new IllegalArgumentException("Category name must not be empty or contain only whitespace");
        }
        // Add more validations as needed (e.g., length restrictions)
    }
}
