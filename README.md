**Important: Don't forget to update the [Candidate README](#candidate-readme) section**

Real-time Transaction Challenge
===============================
## Overview
Welcome to Current's take-home technical assessment for backend engineers! We appreciate you taking the time to complete this, and we're excited to see what you come up with.

Today, you will be building a small but critical component of Current's core banking enging: real-time balance calculation through [event-sourcing](https://martinfowler.com/eaaDev/EventSourcing.html).

## Schema
The [included service.yml](service.yml) is the OpenAPI 3.0 schema to a service we would like you to create and host. 

## Details
The service accepts two types of transactions:
1) Loads: Add money to a user (credit)

2) Authorizations: Conditionally remove money from a user (debit)

Every load or authorization PUT should return the updated balance following the transaction. Authorization declines should be saved, even if they do not impact balance calculation.

You may use any technologies to support the service. We do not expect you to use a persistent store (you can you in-memory object), but you can if you want. We should be able to bootstrap your project locally to test.

## Expectations
We are looking for attention in the following areas:
1) Do you accept all requests supported by the schema, in the format described?

2) Do your responses conform to the prescribed schema?

3) Does the authorizations endpoint work as documented in the schema?

4) Do you have unit and integrations test on the functionality?

# Candidate README
## Bootstrap instructions
*To run this server locally, do the following:*
1) Clone the repository.
2) Open your preferred IDE (e.g., IntelliJ IDEA, Eclipse, or Visual Studio Code).
3) Ensure that your IDE has the necessary Java and Spring Boot plugins installed and configured.
4) In your IDE, locate the main application class (named TransactionServiceApplication.java).
   Right-click on the class and select Run to start the Spring Boot application.
5) Alternatively, you can use the command line to run the application.
   In the terminal, use this command : ./mvnw spring-boot:run
6) Once the application is up and running, you can use a tool like Postman or cURL to send requests to http://localhost:8080/ping and confirm that the application is running as expected. As configured in the application.properties file , the Spring Boot application runs on port 8080.
7) A demo MongoDB database is hosted using Atlas; the connection string is provided in application.properties. To use your own instance, replace the connection string in the file.

## Design considerations
1) I decided to use MongoDB as a persistent store to save User and Transaction data. You can find the schema under /persistence package.
2) As mentioned here - [event-sourcing](https://martinfowler.com/eaaDev/EventSourcing.html), we need to store transaction data somewhere so that even if we lose User data, we can get it back by playing the transaction data in chronological order.
3) For this purpose, a fast persistent store like MongoDB has been chosen. It is also very simple to configure. 
4) More on why MongoDB is a perfect choice for Real Time Payments services can be found here - [MongoDB-Real-Time-Payment](https://www.mongodb.com/resources/basics/real-time-payments)
5) I opted for Spring Boot as the Java framework because it seamlessly integrates with Maven. This combination offers a comprehensive backend application environment perfect for hosting REST APIs.
6) Spring Boot's auto-configuration capabilities further streamline development. It simplifies the integration with MongoDB, allowing you to focus on crafting the business logic for load and authorization.

## Bonus: Deployment considerations
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

## ASCII art
*Optional but suggested, replace this:*
```
                                                                                
                   @@@@@@@@@@@@@@                                               
               @@@@@@@@@@@@@@@@@@@@@                                            
             @@@@@@@@@@@@@@@@@@@@@@@@@@                                         
          @@@@@@@@@@@@@@@@@@@@@@@@                                  @@@@        
        @@@@@@@@@@@@@@@@@@@@@      @@@@@@                        @@@@@@@@@      
     @@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@                 .@@@@@@@@@@@@@@   
   @@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@ 
 @@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@@   @@@@@@@@@@@@@@@@@@@@@@@@@@ 
    @@@@@@@@@@@@@@               @@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@    
      @@@@@@@@@@                     @@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@       
         @@@@                          @@@@@@@@@@@@@@@@@@@@                     
                                          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@         
                                            @@@@@@@@@@@@@@@@@@@@@@@@            
                                               @@@@@@@@@@@@@@@@@@               
                                                    @@@@@@@@                    
```
## License

At CodeScreen, we strongly value the integrity and privacy of our assessments. As a result, this repository is under exclusive copyright, which means you **do not** have permission to share your solution to this test publicly (i.e., inside a public GitHub/GitLab repo, on Reddit, etc.). <br>

## Submitting your solution

Please push your changes to the `main branch` of this repository. You can push one or more commits. <br>

Once you are finished with the task, please click the `Submit Solution` link on <a href="https://app.codescreen.com/candidate/ad5f68d7-757e-4064-ab4b-c56540d68df3" target="_blank">this screen</a>.