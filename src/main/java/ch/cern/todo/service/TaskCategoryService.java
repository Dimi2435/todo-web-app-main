package ch.cern.todo.service;

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
        if (!taskCategoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found");
        }
        validateCategory(category);
        category.setId(id);
        return taskCategoryRepository.save(category);
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
        if (category.getName() == null || category.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name must not be empty");
        }
        // Add more validations as needed
    }
}

