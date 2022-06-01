# What was done?

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
