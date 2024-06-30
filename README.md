# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.3.1/maven-plugin/reference/html/#build-image)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.


### DDD Oder

Once you get the shared 
we make the iam

we make our domain package with model and services package 

the order is value objects, entities, aggregates, commands, queries, repositories and finally exceptions if needed
Also the services where we make the command an query service interfaces

Then we make the infrastructure 
in this case is more complex since we are doing the iam implementation
normally it would only be the persistence with repositories

in here we have authorization, hashing, tokens and persistence
we make the tokens and hashing will be made in the infrastructure but remember that you will need to implement the interfaces in the application package
then we make the authorization and persistence

Next we make the application package with the command and query services implementation
Here we got our command and query services interfaces and we implement them
We got the outbound services and the event handlers for the events
the event handler used here is for the seeding of the entity table of roles
the outbound services are used for HASHING and TOKENS services interface which will be implemented in the infrastructure
Then we make the command and query services implementations 

Finally we make the interfaces for REST since in this example we aren`t using ACL
We make the resources and transformers
Then we make the controllers

Dont forget to enable JPA Auditing and to make the JWT configuration

``` java
# Spring Application Name
spring.application.name=TestingIAMAndOtherStuff
server.port=8080




# Spring DataSource Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/iam_and_stuff?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Spring Data JPA Configuration
spring.jpa.show-sql=true

#Spring Data JPA Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.naming.physical-strategy=group.name.IamAPI.shared.infrastructure.persistence.jpa.configuration.strategy.SnakeCaseWithPluralizedTablePhysicalNamingStrategy

# Application information for documentation

# Elements take their values from the maven pom.xml build-related information
documentation.application.description=@project.description@
documentation.application.version=@project.version@

# JWT Configuration properties
authorization.jwt.secret=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0. AxE9qm4aTZiXvA2G8sblAxjeLhomy7lTQpcPCV6q_7asKyegL3305BcV_EUF950yOeJQqMBplzhP-pAKCiGS0oFLXjNZvUsifCpQCqJfRKFyxpnD8agtbB9UZLEhSEET3lKPSF1Y0Sqbcz1SkFafV0PZ9Hi3HepUtjXQ8zLUxMt-3cBnK9a5I4gc32fq9Pkgt-3Ysnw02SIzdiCyoQGiDDJA9CtJ3RNY1DnOEL6qB4PzwQSrHz8hyXdlotpHQ1Izgms8Sv62w3VKQJBRjnRLNVhszO4tg3YsvXpINEkL4KCKLOCDwLT1Y-Tj8LANn2fbU5XeuDGa43k1bwNXmDH78w
authorization.jwt.expiration.days=7


```