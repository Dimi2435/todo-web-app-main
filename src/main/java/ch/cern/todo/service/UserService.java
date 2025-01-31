package ch.cern.todo.service;

import ch.cern.todo.config.SecurityConfig;
import ch.cern.todo.model.User;
import ch.cern.todo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService { // Removed @Transactional - manage transactions explicitly

    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;


    public UserService(UserRepository userRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
       // this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        // Encode the password before saving
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        return userRepository.findById(id).map(user -> {
            //Encode the password before saving. This could be skipped if the password does not change
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setRoleType(updatedUser.getRoleType());
            return userRepository.save(user);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
