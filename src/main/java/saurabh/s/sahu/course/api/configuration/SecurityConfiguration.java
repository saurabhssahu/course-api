package saurabh.s.sahu.course.api.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
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

    private final UserDetailsService userDetailsService;

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

//    @Bean
//    public AuthenticationManager localAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {
//        return auth.ldapAuthentication()
//                .userDnPatterns("uid={0},ou=people")
//                .groupSearchBase("ou=groups")
//                .groupRoleAttribute("uniquemember")
//                .contextSource()
//                .url("ldap://localhost:8389/dc=springframework,dc=org")
//                .and()
//                .passwordCompare()
//                .passwordEncoder(new LdapShaPasswordEncoder())
//                .passwordAttribute("userPassword");
//    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                // dn: uid=ben,ou=people,dc=springframework,dc=org   ---  here use uid as username
                // dn: cn=mouse\, jerry,ou=people,dc=springframework,dc=org   ---  here use cn as username
                .userDnPatterns("uid={0},ou=people", "cn={0},ou=people") // Uses both pattern
                .groupSearchBase("ou=groups")
                .groupRoleAttribute("cn") // here CNs are used for role attributes. ex: developers, managers
                .contextSource()
                .url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare()
                .passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }

    /**
     * Authorization configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher("/**") // This filter chain matches all remaining requests
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin").hasAnyRole("ADMIN", "MANAGERS") // the CNs should be passed here to authorise
                        .requestMatchers("/user").hasAnyRole("ADMIN", "USER", "DEVELOPERS")
                        .requestMatchers("/", "/login").permitAll()
                        .anyRequest().fullyAuthenticated())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
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