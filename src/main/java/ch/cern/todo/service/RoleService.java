// package ch.cern.todo.service;

// import ch.cern.todo.model.Role; // Assuming you have a Role model
// import ch.cern.todo.repository.RoleRepository; // Assuming you have a RoleRepository

// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import ch.cern.todo.exception.ResourceNotFoundException;

// import java.util.List;

// import javax.management.RuntimeErrorException;

// /**
//  * Service class for managing Role entities.
//  */
// @Service
// @Transactional
// public class RoleService {
//     private final RoleRepository roleRepository;

//     public RoleService(RoleRepository roleRepository) {
//         this.roleRepository = roleRepository;
//     }

//     /**
//      * Creates a new Role.
//      *
//      * @param role the Role to create
//      * @return the created Role
//      */
//     @Transactional
//     public Role createRole(Role role) {
//         try {
//             // Existing code to save the role
//             validateRole(role);
//             return roleRepository.save(role);
//         } catch (DataIntegrityViolationException e) {
//             // Handle unique constraint violation
//             throw new RuntimeException("Role with name '" + role.getName() + "' already exists.");
//         } catch (Exception e) {
//             // Handle other database errors
//             throw new RuntimeException("Failed to create role: " + e.getMessage(), e);
//         }
//     }

//     /**
//      * Retrieves a Role by its ID.
//      *
//      * @param id the ID of the Role
//      * @return the Role if found
//      */
//     public Role getRoleById(Long id) {
//         return roleRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + id));
//     }

//     /**
//      * Retrieves all Roles.
//      *
//      * @return a list of all Roles
//      */
//     public List<Role> getAllRoles() {
//         return roleRepository.findAll();
//     }

//     /**
//      * Updates an existing Role.
//      *
//      * @param id   the ID of the Role to update
//      * @param role the updated Role data
//      * @return the updated Role
//      */
//     public Role updateRole(Long id, Role role) {
//         if (!roleRepository.existsById(id)) {
//             throw new ResourceNotFoundException("Role not found with ID: " + id);
//         }
//         validateRole(role);
//         role.setId(id);
//         return roleRepository.save(role);
//     }

//     /**
//      * Deletes a Role by its ID.
//      *
//      * @param id the ID of the Role to delete
//      */
//     public void deleteRole(Long id) {
//         if (!roleRepository.existsById(id)) {
//             throw new ResourceNotFoundException("Role not found with ID: " + id);
//         }
//         roleRepository.deleteById(id);
//     }

//     /**
//      * Validates the Role data.
//      *
//      * @param role the Role to validate
//      */
//     private void validateRole(Role role) {
//         if (role.getName() == null || role.getName().isEmpty()) {
//             throw new IllegalArgumentException("Role name must not be empty");
//         }
//         // Add more validations as needed
//     }
// }