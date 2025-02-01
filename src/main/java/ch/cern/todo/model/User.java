package ch.cern.todo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author Dimitrios Milios
 */

/**
 * Entity class representing a User in the system. Maps to the 'users' database
 * table.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING) // Crucial for storing as string in H2
    @Column(name = "ROLE_TYPE", nullable = false)
    private RoleType roleType;

    /**
     * No-argument constructor required by JPA/Hibernate.
     */
    public User() {
    }

    /**
     * Constructor for creating a User object.
     * 
     * @param username The username of the user.
     * @param password The password of the user.
     * @param email    The email address of the user.
     * @param roleType The role of the user.
     */
    public User(String username, String password, String email, RoleType roleType) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleType = roleType;
    }

    /**
     * Getter for the user's ID.
     * 
     * @return The ID of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the user's ID. Used primarily by JPA/Hibernate.
     * 
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the user's username.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the user's username.
     * 
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the user's password.
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the user's password. Should ideally perform password hashing.
     * 
     * @param password The password to set.
     */
    public void setPassword(String password) {
        if (password != null) { // check for null input to prevent NullPointerException
            this.password = password;
        }
    }

    /**
     * Getter for the user's email address.
     * 
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the user's email address.
     * 
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for the user's role type.
     * 
     * @return The role type of the user.
     */
    public RoleType getRoleType() {
        return roleType;
    }

    /**
     * Setter for the user's role type.
     * 
     * @param roleType The role type to set.
     */
    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    /**
     * Returns a string representation of the User object. Useful for logging and
     * debugging.
     * 
     * @return A string representation of the User object.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roleType='" + roleType + '\'' +
                '}';
    }
}