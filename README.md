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
* JUnit + Spring Boot Test
* Thymeleaf

# Tests 
Included Integration tests for the backend.

# How to build?
mvn clean install

# How to run the application?

java -jar target/kalaha-0.0.1-SNAPSHOT.jar  
Open https://localhost:8080

# Further improvements
Due to limited time, the game was implemented with bare minimum requirements.
Below is the list of further improvements.

## Security
* Login using Social accounts.

## UI
* Thymleaf full capabilites can be utilized for templating and spring security implementation
* Use bundler to compress static resources (js, css, images) to enhance network response 

## Persistence
* introduce data storage to keep a history of games and be able to generate statistics, keep JWT tokens, etc
* extend user entities and keep them in DB for further usage

## Game Design
* Implement http session for online multiplayer.
