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
    - Scans through the application to understand the intend of the classes - controller, service, etc.
- Starts Tomcat server.
    - The Apache Tomcat is actually a server and a servlet container.

`Controller`- A Java class, marked with annotations which lets Spring know what is the URL it is mapping to and what
should happen when a request comes to that URL.

`Service`- In Spring Business Services are Singleton (ensures only one instance of itself exists).

Bill of Materials - The preset list of possible combinations of jars that works well without issues.