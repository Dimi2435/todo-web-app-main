package ch.cern.todo.config;

//import ch.cern.todo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * Security configuration class for managing authentication and authorization.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // private final UserService userService;

    // public SecurityConfig(UserService userService) {
    // this.userService = userService;
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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

    // @Bean
    // public UserDetailsService userDetailsService(UserRepository userRepository) {
    // JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
    // manager.setDataSource(userRepository.getJpaContext().getEntityManagerFactory()
    // .getDataSource());
    // return manager;
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    Exception {
    http
    .authorizeRequests()
    .requestMatchers("/v3/api-docs/**").permitAll()
    .requestMatchers("/swagger-ui.html").permitAll()
    .requestMatchers("/swagger-ui/**").permitAll()
    .requestMatchers("/h2-console/**").permitAll()
    .requestMatchers("/api/tasks/**").authenticated()
    .requestMatchers("/api/users/**").authenticated()
    .requestMatchers("/api/roles/**").authenticated()
    .requestMatchers("/api/categories/**").hasRole("ADMIN");

    http.headers().frameOptions().disable();

    http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
    http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/users/**"));    
    http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/tasks/**"));
    http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/categories/**"));
    http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/roles**"));


    http.formLogin()
    .permitAll();

    http.httpBasic();

    return http.build();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
    //     .and().csrf().ignoringAntMatchers("/h2-console/**")
    //     .and().headers().frameOptions().sameOrigin();
    // }

}
