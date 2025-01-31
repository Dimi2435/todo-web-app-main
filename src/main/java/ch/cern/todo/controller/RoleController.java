// package ch.cern.todo.controller;

// import ch.cern.todo.model.Role;
// import ch.cern.todo.service.RoleService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// /**
//  * Controller class for managing Role entities.
//  */
// @RestController
// @RequestMapping("/api/roles")
// public class RoleController {
//     private final RoleService roleService;

//     public RoleController(RoleService roleService) {
//         this.roleService = roleService;
//     }

//     /**
//      * Creates a new Role.
//      *
//      * @param role the Role to create
//      * @return ResponseEntity containing the created Role
//      */
//     @PostMapping
//     public ResponseEntity<Role> createRole(@RequestBody Role role) {
//         Role createdRole = roleService.createRole(role);
//         return ResponseEntity.ok(createdRole);
//     }

//     /**
//      * Updates an existing Role.
//      *
//      * @param id   the ID of the Role to update
//      * @param role the updated Role data
//      * @return ResponseEntity containing the updated Role
//      */
//     @PutMapping("/{id}")
//     public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
//         Role updatedRole = roleService.updateRole(id, role);
//         return ResponseEntity.ok(updatedRole);
//     }

//     /**
//      * Deletes a Role by its ID.
//      *
//      * @param id the ID of the Role to delete
//      * @return ResponseEntity with no content
//      */
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
//         roleService.deleteRole(id);
//         return ResponseEntity.noContent().build();
//     }

//     /**
//      * Retrieves all Roles.
//      *
//      * @return ResponseEntity containing a list of all Roles
//      */
//     @GetMapping
//     public ResponseEntity<List<Role>> getAllRoles() {
//         List<Role> roles = roleService.getAllRoles();
//         return ResponseEntity.ok(roles);
//     }

//     /**
//      * Retrieves a Role by its ID.
//      *
//      * @param id the ID of the Role
//      * @return ResponseEntity containing the Role if found, or not found status
//      */
//     @GetMapping("/{id}")
//     public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
//         return ResponseEntity.ok(roleService.getRoleById(id));
//     }
// } 