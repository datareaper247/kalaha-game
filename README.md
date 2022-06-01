# What was done?
# Architecture
It was chosen to use the most simple architectural type - Multi-Layered Architecture without persistence layer since in this case it is not needed since we don't introduce data storage.
From user's standpoint, UI is communicating with back-end over REST and form-submission (in case of login flow (for simplicity))

# Structure
The project is wrapped up with Maven 3. It consists of 3 modules (domain (holds domain objects and exceptions), service (business logic), web (web API and representation)).
The main game flow is implemented through Spring-like Chain of Responsibility pattern using Ordered interface. Every turn goes through pipeline of RulesApplier classes. 
Each of them is applying the rule, which is responsible for.
All these implementations and calling them are controlled by RulesOrchestrator, which in turn is used by web controller to populate received data to UI.
All the validation, rules applying are made by this chain. 

# Security
Server supports SSL/TLS with 'want' client authentication on 8443 port. Each user is limited to have only 1 session.
Keystore is located at web/resources and used by Tomcat to establish connection between server and client.
Clients use JSESSIONID over secure cookie with CSRF token in header (against XSRF attacks) to keep them alive.

# Stack
* Java 8
* Spring Boot/MVC/Security
* Lombok
* Commons Lang/Collections
* JUnit + Spring Boot Test
* Pitest
* Thymeleaf
* Tomcat 8

# Tests 
### 100% line coverage and 97% mutation coverage
Only service layer was covered by JUnit tests using Spring Boot Test library, since web layer is a kind of dispatcher to service layer business logic. 
Line coverage and mutation coverage were verified by PiTest. 
To check PiTest reports - you need to run at root project folder 

mvn org.pitest:pitest-maven:mutationCoverage 

After running all the tests and performing all the mutations, you need to open /service/target/pit-reports/datetimestamp/index.html

# JavaDocs
Code is documented with in-depth details. You can pass through the classes to follow the flow along with reading comments

# Users
There are 2 users, who are the players:
* south/password1
* north/password2

Take a look at SpringSecurityConfig class to find out how they are defined

# How to build?
mvn clean install

# How to run the application?

java -jar target/kalaha-0.0.1-SNAPSHOT.jar  
Open https://localhost:8080

# Further improvements
Due to limited time, the game was implemented with bare minimum requirements.
Below is the list of further improvements.

## Security


## UI
* Thymleaf full capabilites can be utilized for templating and spring security implementation
* Use bundler to compress static resources (js, css, images) to enhance network response 

## Persistence
* introduce data storage to keep a history of games and be able to generate statistics, keep JWT tokens, etc
* extend user entities and keep them in DB for further usage

## Game Design
* Implement http session

## Misc
* implement exception dictionary with mapping exception-to-message-shown-to-user
* introduce resource management system to support i18n, push locales files to UI to show appropriately 
* implement tests for web layer. It was skipped since web layer is a kind of dispatcher to service layer.