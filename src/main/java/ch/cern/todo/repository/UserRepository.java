package ch.cern.todo.repository;

import ch.cern.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // No need to define findById() - JpaRepository provides it automatically
}