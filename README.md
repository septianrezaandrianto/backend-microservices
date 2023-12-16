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
1. be-config-service :
   a. Config Server
   
2. be-discovery :
   a. Spring Boot Actuator
   b. Config Client
   c. Eureka Server

3. be-gateway\n
   a. Spring Boot Actuator\n
   b. Config Client
   c. Reactive gateway
   d. Eureka Discovery Client
   e. Lombok
   f. Spring webflux
   g. Gson
   h. jjwt-api
   i. jjwt-impl
   j. jjwt-jackson

4. be-auth
   a. Spring Boot Actuator
   b. Spring Data JPA
   c. Spring Security
   d. Eureka Discovery Client
   e. Config Client
   f. H2 Database
   g. Lombok
   h. Spring Boot Starter Validation
   i. jjwt-api
   j. jjwt-impl
   k. jjwt-jackson

5. be-catalog
   a. Spring Boot Actuator
   b. Spring Data JPA
   c. Eureka Discovery Client
   d. Config Client
   f. H2 Database
   g. Lombok
   h. Spring Boot Starter Validation

6. be-report
   a. Spring Boot Actuator
   b. Spring Data JPA
   c. Eureka Discovery Client
   d. Config Client
   f. H2 Database
   g. Lombok
   h. Spring Boot Starter Validation
   i. OpenFeign
