
Real-Time Bank Transaction Application 
===============================
## Overview
This project is a Java Spring Boot application that provides a RESTful API for real-time balance calculation through event-sourcing. It accepts two types of transactions: loads (credit) and authorizations (debit). The application uses MongoDB as the persistent store for user and transaction data.

The project uses concepts of Event Sourcing. More on this here : https://martinfowler.com/eaaDev/EventSourcing.html

## Schema
The application follows the OpenAPI 3.0 schema defined in the included service.yml file.

## Bootstrap instructions
*To run this server locally, do the following:*
1) Clone the repository.
2) Open your preferred IDE (e.g., IntelliJ IDEA, Eclipse, or Visual Studio Code).
3) Ensure that your IDE has the necessary Java and Spring Boot plugins installed and configured.
4) In your IDE, locate the main application class (named TransactionServiceApplication.java).
   Right-click on the class and select Run to start the Spring Boot application.
5) Alternatively, you can use the command line to run the application.
   In the terminal, use this command : ./mvnw spring-boot:run
6) Once the application is up and running, you can use a tool like Postman or cURL to send requests to http://localhost:8080/ping and confirm that the application is running as expected. The Swagger API documentation will be available at this Url : http://localhost:8080/swagger-ui/index.html.
7) A demo MongoDB database is hosted using Atlas; the connection string is provided in application.properties. To use your own instance, replace the connection string in the file.

## Design considerations
Persistence:
* MongoDB was selected as the persistent store for User and Transaction data. Its schema can be found in the /persistence package.
* MongoDB's ability to store historical data aligns with the event sourcing approach, allowing reconstruction of User data from past transactions if needed.
* For this purpose, a fast persistent store like MongoDB with its ease of configuration is well-suited for this application.
* More on why MongoDB is a perfect choice for Real Time Payments services can be found here - [MongoDB-Real-Time-Payment](https://www.mongodb.com/resources/basics/real-time-payments)

Backend Framework:
* Spring Boot was chosen as the Java framework for this project due to its seamless integration with Maven. This combination provides a comprehensive backend environment ideal for building and deploying REST APIs.
* Spring Boot's auto-configuration capabilities further streamline development. It simplifies the integration with MongoDB, allowing you to focus on crafting the business logic for load and authorization.

API Documentation:
* Spring Boot integrates seamlessly with Swagger, providing comprehensive API documentation. This simplifies API testing and exploration. Access the Swagger documentation at http://localhost:8080/swagger-ui/index.html once the application is running.

Current Assumption: The current design assumes all transactions are processed in a single currency - "USD".

## Deployment considerations
 If I were to deploy this, I would :
1) Containerize the application using Docker :
   Create a Dockerfile to package the Spring Boot application.
   Build and test the Docker image locally.
2) Choose a Cloud Platform :
   Evaluate cloud providers (AWS, GCP, Azure) based on cost, scalability, and managed services.
3) Implement Container Orchestration:
   Integrate the Docker image with Kubernetes or Amazon ECS.
   Configure Deployments, Services, and Ingress resources.
4) Establish a CI/CD Pipeline:
   Set up a CI pipeline to build, test, and push the Docker image.
   Integrate the CI pipeline with a CD tool for automated deployments.
5) Configure Monitoring and Logging:
   Integrate with monitoring solutions (Prometheus, Grafana) for metrics.
6) Optimize for Scalability and High Availability:
   Configure autoscaling based on resource utilization or traffic.
   Implement load balancing and redundancy for fault tolerance.
6) Establish a Disaster Recovery Plan:
   Implement regular backups of application data and configuration.

