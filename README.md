# backend-service

## List of Service
1. be-config-service : For put all config from another service,
2. be-discovery : Service Discovery helps us by providing a database of available service instances so that services can be discovered, registered, and de-registered based on usage,
3. be-gateway : For routing and filtering incoming requests to microservices,
4. be-auth : Authorization service for handling security,
5. be-catalog : as a main project for handling crud,
6. be-report : as a main project for generate report.


## Rules of game
1. The first you must running be-config-service before you run another service
2. run the be-discovery service and accedd this url from browswe for monitoring your service http://localhost:8761/


## Stack 
1. Java JDK 21
2. Spring Boot 3.2.0
3. Maven 3.9.1

## Libraries :
### 1. be-config-service :
   a. Config Server
   
### 2. be-discovery :
   a. Spring Boot Actuator
   b. Config Client
   c. Eureka Server

### 3. be-gateway
   a. Spring Boot Actuator <br>
   b. Config Client <br>
   c. Reactive gateway  <br>
   d. Eureka Discovery Client <br>
   e. Lombok <br>
   f. Spring webflux <br>
   g. Gson <br>
   h. jjwt-api <br>
   i. jjwt-impl <br>
   j. jjwt-jackson

### 4. be-auth
   a. Spring Boot Actuator <br>
   b. Spring Data JPA <br>
   c. Spring Security <br>
   d. Eureka Discovery Client <br>
   e. Config Client <br>
   f. H2 Database <br>
   g. Lombok <br>
   h. Spring Boot Starter Validation <br>
   i. jjwt-api <br>
   j. jjwt-impl <br>
   k. jjwt-jackson

### 5. be-catalog
   a. Spring Boot Actuator <br>
   b. Spring Data JPA <br>
   c. Eureka Discovery Client <br>
   d. Config Client <br>
   f. H2 Database <br>
   g. Lombok <br>
   h. Spring Boot Starter Validation

### 6. be-report
   a. Spring Boot Actuator <br>
   b. Spring Data JPA <br>
   c. Eureka Discovery Client <br>
   d. Config Client <br>
   f. H2 Database <br>
   g. Lombok <br>
   h. Spring Boot Starter Validation <br>
   i. OpenFeign