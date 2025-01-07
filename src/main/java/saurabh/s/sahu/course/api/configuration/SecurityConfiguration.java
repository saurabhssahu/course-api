package saurabh.s.sahu.course.api.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * For in-memory authentication
     */
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = getUserDetails("user", "user", "USER");
//        UserDetails admin = getUserDetails("admin", "admin", "ADMIN");
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    /**
     * For embedded H2 db with default schema and tables
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    /**
     * For H2 datasource JDBC authentication
     */

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        // In case we use different datasource like sql, oracle and different tables like my_users
        // users.setUsersByUsernameQuery("select username, password, enabled from my_users where username = ?");

        // In case we use different datasource like sql, oracle and different tables like my_authorities
        //users.setAuthoritiesByUsernameQuery("select username, authority from my_authorities where username = ?");

        // When using default schema and tables
        users.createUser(getUserDetails("default_user", "default user", "USER"));
        users.createUser(getUserDetails("default_admin", "default admin", "ADMIN"));

        return users;
    }

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