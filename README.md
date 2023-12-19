

# Offline First Concept Approach

## Overview

This project demonstrates the implementation of the Offline First concept using Java, Spring Boot microservices, Docker, Docker Compose, and PostgreSQL. The application is built with the aim of ensuring functionality even in offline scenarios, utilizing containerization for seamless deployment and management.

## Tools Used

- **Java:** The primary programming language used for building the microservices.
- **Spring Boot Microservices:** Leveraging the Spring Boot framework to create scalable and efficient microservices.
- **Maven:** Build tool used to manage the project's build lifecycle.
- **Jib Maven Plugin:** Simplifies the process of building Docker images without the need for a Docker daemon.
- **Docker Compose:** Orchestrates the deployment of multiple containers, simplifying the setup and configuration.
- **PostgreSQL:** Database management system employed to store and manage data.

## Microservices Architecture

The project is structured around the following microservices:

1. **Blogging Microservice:** Handles the core functionality related to blogging.
2. **API Gateway:** Acts as an entry point for external clients and routes requests to the appropriate microservices.
3. **Discovery Service:** Facilitates service discovery, allowing microservices to find and communicate with each other dynamically.

## Building and Running the Application Locally

To build and run the application locally, follow these steps:

1. **Build and Deploy Spring Boot Microservices Images using Jib:**
   ```bash
   # Navigate to the microservice directory
   cd offline-first-concept/
   
   # Replace 'your-microservice-name' with the actual name of your microservice
   mvn clean compile jib:build -Dimage=your-docker-hub-username/your-microservice-name
   ```

2. **Set up docker compose file and start the Microservices Using Docker Compose:**
   ```bash
   docker-compose up -d
   ```

3. **Access the Application:**
    - The API Gateway will be available at `http://localhost:8081`.
    - Explore other microservices through the API Gateway.

## Offline First Concept

The Offline First concept ensures that the application remains functional even when the network connection is unavailable. This is achieved by designing microservices to handle offline scenarios gracefully, enabling users to perform essential tasks without interruption.

## Additional Considerations

- **Container Naming Convention:**
  The Docker Compose configuration uses the container name "offline-first-concept" to encapsulate the entire application stack.

- **Database Setup:**
  PostgreSQL is used as the database, and its configuration can be found in the respective microservices.

- **Docker Hub Repository:**
  Images are uploaded to Docker Hub using the Jib Maven Plugin for convenient sharing and deployment.



