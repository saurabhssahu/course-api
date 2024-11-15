@SpringBootApplication

@SpringBootApplication annotation can be used to enable those three features, that is:
- @EnableAutoConfiguration: enable Spring Boot’s auto-configuration mechanism
- @ComponentScan: enable @Component scan on the package where the application is located (see the best practices)
- @Configuration: allow to register extra beans in the context or import additional configuration classes

The class with the main method creates Servlet container, starts it and hosts the application in it.

`SpringApplication.run`-
- Sets up default configuration.
- Starts Spring application context.
    - Spring is a container for all the code that runs on the application server.
    - This container is called ApplicationContext.
- Performs class path scan.
    - Scans through the application to understand the intent of the classes - controller, service, etc.
- Starts Tomcat server.
    - The Apache Tomcat is actually a server and a servlet container.

`Controller`- A Java class, marked with annotations which lets Spring know what is the URL it is mapping to and what
should happen when a request comes to that URL.

`Service`- In Spring Business Services are Singleton (ensures only one instance of itself exists).

Bill of Materials - The preset list of possible combinations of jars that works well without issues.

[Spring Boot common application properties](https://docs.spring.io/spring-boot/appendix/application-properties/index.html).

Spring Data JPA -  lets you do Object Relational Mapping(ORM) when you are connected to a relational database.

----

[@Transactional annotation](https://www.geeksforgeeks.org/spring-boot-transaction-management-using-transactional-annotation/)
[Spring Boot Actuator](https://www.geeksforgeeks.org/spring-boot-actuator/)

----

Spring Reactive

Blocking programming model
Reactive model

----

Spring Security

All the OS, JVM, App level security are taken care of.
We are talking about Application level security. 
- Username/ password authentication
- SSO / Okta / LDAP
- App level authorisation
- Inta app authorisation like OAuth
- Microservice security (using tokens. JWT)
- Method level security

5 core concepts in Spring security:
1. Authentication
2. Authorisation
3. Principal
4. Granted Authority
5. Roles

---

1. Authentication: Who is this user?
   - Knowledge based authentication: password, pin code, secret question
   - Possession based authentication: phone/text messages, keycards and badges, access token device
   - Multifactor Authentication: 2FA - enter password and then verify your text message

2. Authorisation: Are they allowed to do this?
   - For authorisation, we need authentication first.

-[Difference between Authentication and Authorization](https://www.geeksforgeeks.org/difference-between-authentication-and-authorization/)

3. Principal: currently logged-in user/account
   - In google I have account A and B, if I am logged in as A then principal is A.

4. Granted Authority: way of providing authorisation.
   - permissions allowed to a given user.
   - Authorities are fine-grained.

5. Roles: group of authorities that are assigned together.
   - Store manager, dept. manager and clerks have certain authorities. Any new clerk comes based on roles we can 
     provide authorities
   - Roles are coarse-grained.

[Difference between Roles & Granted Authority](https://www.geeksforgeeks.org/difference-between-role-and-grantedauthority-in-spring-security/)

Servlets and Filters: Servlets are mapped to specific URLS while filters are mapped to all URLS
![Servlets and filters](../images/servlets-and-filters.png)

Spring Security default behaviour:
1. Adds mandatory authentication for URLs.
2. Adds login form.
3. Handles login error.
4. Creates a user and sets a default password.

AuthenticationManager - manages authentication in a SpringBoot Security app. It has a method called `authenticate()`.
@EnableWebSecurity - tells spring security that it is web security configuration.

HttpSecurity - helps configure what are the paths and what are the access restrictions for those paths
![WebSecurityConfigurerAdapter](../images/WebSecurityConfigurerAdapter.png) - for configuring authentication and authorisation.