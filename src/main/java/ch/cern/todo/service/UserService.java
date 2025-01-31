// package ch.cern.todo.service;

// import ch.cern.todo.model.User; // Assuming you have a User model
// import ch.cern.todo.repository.UserRepository; // Assuming you have a UserRepository
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.List;

// import ch.cern.todo.exception.ResourceNotFoundException;

// /**
//  * Service class for managing User entities.
//  */
// @Service
// @Transactional
// public class UserService {
//     private final UserRepository userRepository;

//     public UserService(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }

//     /**
//      * Creates a new User.
//      *
//      * @param user the User to create
//      * @return the created User
//      */
//     public User createUser(User user) {
//         validateUser(user);
//         return userRepository.save(user);
//     }

//     /**
//      * Retrieves a User by its ID.
//      *
//      * @param id the ID of the User
//      * @return the User if found
//      */
//     public User getUserById(Long id) {
//         return userRepository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
//     }

//     /**
//      * Retrieves all Users.
//      *
//      * @return a list of all Users
//      */
//     public List<User> getAllUsers() {
//         return userRepository.findAll();
//     }

//     /**
//      * Updates an existing User.
//      *
//      * @param id   the ID of the User to update
//      * @param user the updated User data
//      * @return the updated User
//      */
//     public User updateUser(Long id, User user) {
//         if (!userRepository.existsById(id)) {
//             throw new ResourceNotFoundException("User not found with ID: " + id);
//         }
//         validateUser(user);
//         user.setId(id);
//         return userRepository.save(user);
//     }

//     /**
//      * Deletes a User by its ID.
//      *
//      * @param id the ID of the User to delete
//      */
//     public void deleteUser(Long id) {
//         if (!userRepository.existsById(id)) {
//             throw new ResourceNotFoundException("User not found with ID: " + id);
//         }
//         userRepository.deleteById(id);
//     }

//     /**
//      * Validates the User data.
//      *
//      * @param user the User to validate
//      */
//     private void validateUser(User user) {
//         if (user.getUsername() == null || user.getUsername().isEmpty()) {
//             throw new IllegalArgumentException("Username must not be empty");
//         }
//         if (user.getEmail() == null || user.getEmail().isEmpty()) {
//             throw new IllegalArgumentException("Email must not be empty");
//         }
//         // Add more validations as needed
//     }
// } 