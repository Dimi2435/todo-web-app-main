package ch.cern.todo.config;

//import ch.cern.todo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * @author Dimitrios Milios
 */

/**
 * Security configuration class for managing authentication and authorization.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Creates a BCryptPasswordEncoder bean for password encoding.
     * This bean is used to securely hash passwords before storing them in the
     * database.
     * 
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates an InMemoryUserDetailsManager bean for user authentication.
     * This method defines two users: "admin" and "user", with their respective
     * roles and passwords. This is for demonstration only.
     * In a production system, use a persistent user store.
     * 
     * @return An InMemoryUserDetailsManager instance.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = List.of(
                User.withUsername("admin")
                        .password(passwordEncoder().encode("admin123"))
                        .roles("ADMIN")
                        .build(),
                User.withUsername("user")
                        .password(passwordEncoder().encode("user123"))
                        .roles("USER")
                        .build());
        return new InMemoryUserDetailsManager(users);
    }

    /**
     * Configures the Spring Security filter chain for web security.
     * This method defines authorization rules for different endpoints, including:
     * - Permitting access to Swagger and H2 Console
     * - Requiring ADMIN role for access to /api/tasks, /api/users, and
     * /api/categories endpoints.
     * - Enabling HTTP Basic authentication
     * - Disabling CSRF protection for specific endpoints for testing purposes
     * (remove or adjust this in production).
     * 
     * @param http The HttpSecurity object to configure.
     * @return A SecurityFilterChain instance.
     * @throws Exception if there is an error during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                // .requestMatchers("/api/tasks/**").authenticated()
                .requestMatchers("/api/tasks/**").hasRole("ADMIN")
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                // .requestMatchers("/api/users/**").authenticated()
                // .requestMatchers("/api/roles/**").authenticated()
                .requestMatchers("/api/categories/**").hasRole("ADMIN");

        http.headers().frameOptions().disable();

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/users/**"));
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/tasks/**"));
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/categories/**"));
        // http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/roles**"));

        http.formLogin()
                .permitAll();

        http.httpBasic();

        return http.build();
    }

}
