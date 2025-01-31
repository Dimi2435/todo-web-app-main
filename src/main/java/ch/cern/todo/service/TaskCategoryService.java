package ch.cern.todo.service;

import ch.cern.todo.exception.ResourceNotFoundException;
import ch.cern.todo.model.TaskCategory;
import ch.cern.todo.repository.TaskCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing TaskCategory entities.
 */
@Service
@Transactional
public class TaskCategoryService {
    private final TaskCategoryRepository taskCategoryRepository;

    public TaskCategoryService(TaskCategoryRepository taskCategoryRepository) {
        this.taskCategoryRepository = taskCategoryRepository;
    }

    /**
     * Creates a new TaskCategory.
     *
     * @param category the TaskCategory to create
     * @return the created TaskCategory
     */
    public TaskCategory createCategory(TaskCategory category) {
        validateCategory(category);
        return taskCategoryRepository.save(category);
    }

    /**
     * Retrieves a TaskCategory by its ID.
     *
     * @param id the ID of the TaskCategory
     * @return an Optional containing the TaskCategory if found, or empty if not
     */
    public Optional<TaskCategory> getCategoryById(Long id) {
        return taskCategoryRepository.findById(id);
    }

    /**
     * Retrieves all TaskCategories.
     *
     * @return a list of all TaskCategories
     */
    public List<TaskCategory> getAllCategories() {
        return taskCategoryRepository.findAll();
    }

    /**
     * Updates an existing TaskCategory.
     *
     * @param id       the ID of the TaskCategory to update
     * @param category the updated TaskCategory data
     * @return the updated TaskCategory
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
     * Deletes a TaskCategory by its ID.
     *
     * @param id the ID of the TaskCategory to delete
     */
    public void deleteCategory(Long id) {
        if (!taskCategoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found");
        }
        taskCategoryRepository.deleteById(id);
    }

    /**
     * Validates the TaskCategory data.
     *
     * @param category the TaskCategory to validate
     */
    private void validateCategory(TaskCategory category) {
        if (category.getName() == null || category.getName().isBlank()) { // Use isBlank to check for empty or whitespace-only names
            throw new IllegalArgumentException("Category name must not be empty or contain only whitespace");
        }
        // Add more validations as needed (e.g., length restrictions)
    }
}

