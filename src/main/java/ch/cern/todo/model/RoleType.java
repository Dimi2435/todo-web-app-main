package ch.cern.todo.model;

/**
 * @author Dimitrios Milios
 */

/**
 * Enumeration representing the different user roles in the application.
 * This enum defines the possible roles a user can have. It's used to control
 * access to resources and features.
 */
public enum RoleType {
    /**
     * Represents the administrator role, which has full access to all features and
     * resources.
     */
    ADMIN,
    /**
     * Represents the standard user role, which has limited access to resources and
     * features.
     */
    USER
}
