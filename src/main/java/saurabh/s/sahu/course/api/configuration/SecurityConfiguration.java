package saurabh.s.sahu.course.api.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * For in-memory authentication
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = getUserDetails("user", "user", "USER");
        UserDetails admin = getUserDetails("admin", "admin", "ADMIN");

        return new InMemoryUserDetailsManager(user, admin);
    }

//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/", "/login").permitAll())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    private UserDetails getUserDetails(String username, String password, String... roles) {
        return User.builder()
                .username(username)
                .password("{noop}" + password) // Use a NoOpPasswordEncoder by prefixing with {noop}
                .roles(roles)
                .disabled(false)
                .build();
    }
}