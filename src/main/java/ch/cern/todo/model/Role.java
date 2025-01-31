// package ch.cern.todo.model;

// import jakarta.persistence.*;
// import java.util.HashSet;
// import java.util.Set;

// /**
//  * Entity class representing a Role in the system.
//  */
// @Entity
// @Table(name = "ROLES")
// public class Role {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "ROLE_ID")
//     private Long id;

//     @Column(name = "ROLE_NAME", nullable = false, unique = false, length = 50)
//     private String name;

//     @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
//     private Set<User> users = new HashSet<>();

//     // Default constructor
//     public Role() {}

//     // Constructor with parameters
//     public Role(String name) {
//         this.name = name;
//     }

//     // Getters and Setters
//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public Set<User> getUsers() {
//         return users;
//     }

//     public void setUsers(Set<User> users) {
//         this.users = users;
//     }

//     // Override toString, equals, and hashCode for better usability
//     @Override
//     public String toString() {
//         return "Role{" +
//                 "id=" + id +
//                 ", name='" + name + '\'' +
//                 '}';
//     }

//     // Override equals and hashCode for proper comparison
//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (!(o instanceof Role)) return false;
//         Role role = (Role) o;
//         return id != null && id.equals(role.id);
//     }

//     @Override
//     public int hashCode() {
//         return 31;
//     }
// }