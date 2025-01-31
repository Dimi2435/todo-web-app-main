// package ch.cern.todo.controller;

// import ch.cern.todo.model.User;
// import ch.cern.todo.service.UserService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// /**
//  * Controller class for managing User entities.
//  */
// @RestController
// @RequestMapping("/api/users")
// public class UserController {
//     private final UserService userService;

//     public UserController(UserService userService) {
//         this.userService = userService;
//     }

//     /**
//      * Creates a new User.
//      *
//      * @param user the User to create
//      * @return ResponseEntity containing the created User
//      */
//     @PostMapping
//     public ResponseEntity<User> createUser(@RequestBody User user) {
//         User createdUser = userService.createUser(user);
//         return ResponseEntity.ok(createdUser);
//     }

//     /**
//      * Updates an existing User.
//      *
//      * @param id   the ID of the User to update
//      * @param user the updated User data
//      * @return ResponseEntity containing the updated User
//      */
//     @PutMapping("/{id}")
//     public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
//         User updatedUser = userService.updateUser(id, user);
//         return ResponseEntity.ok(updatedUser);
//     }

//     /**
//      * Deletes a User by its ID.
//      *
//      * @param id the ID of the User to delete
//      * @return ResponseEntity with no content
//      */
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//         userService.deleteUser(id);
//         return ResponseEntity.noContent().build();
//     }

//     /**
//      * Retrieves all Users.
//      *
//      * @return ResponseEntity containing a list of all Users
//      */
//     @GetMapping
//     public ResponseEntity<List<User>> getAllUsers() {
//         List<User> users = userService.getAllUsers();
//         return ResponseEntity.ok(users);
//     }

//     /**
//      * Retrieves a User by its ID.
//      *
//      * @param id the ID of the User
//      * @return ResponseEntity containing the User if found, or not found status
//      */
//     @GetMapping("/{id}")
//     public ResponseEntity<User> getUserById(@PathVariable Long id) {
//         return ResponseEntity.ok(userService.getUserById(id));
//     }
// } 