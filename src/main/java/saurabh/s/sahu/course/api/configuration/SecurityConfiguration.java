package saurabh.s.sahu.course.api.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

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
     * For embedded H2 db with default/custom schema and tables
     */
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                // comment below line then it will create tables and users through schema.sql and data.sql
////                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//                .build();
//    }

    /**
     * For H2 datasource JDBC authentication manager
     */

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//
//        // In case we use different datasource like sql, oracle and different tables like my_users
//        // users.setUsersByUsernameQuery("select username, password, enabled from my_users where username = ?");
//
//        // In case we use different datasource like sql, oracle and different tables like my_authorities
//        // users.setAuthoritiesByUsernameQuery("select username, authority from my_authorities where username = ?");
//
//        // When using default schema and tables, used to  create users with their authorities
//        // comment below lines then it will create through schema.sql and data.sql
    /// /        users.createUser(getUserDetails("default_user", "default user", "USER"));
    /// /        users.createUser(getUserDetails("default_admin", "default admin", "ADMIN"));
//
//        return users;
//    }

    private UserDetailsService userDetailsService;

    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Authentication configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Authorization configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/**") // This filter chain matches all remaining requests
                .authorizeHttpRequests(auth -> auth
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