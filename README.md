# spring-cloud-sample

[Spring Cloud Microservices After Netflix Era](https://medium.com/@Ankitthakur/spring-boot-with-kafka-integration-part-2-kafka-consumer-cf08f9c040e0)

## Spring Cloud Config
## Spring Cloud Consul
## Spring Cloud Load Balancer
## Spring Cloud Gateway
## Resilience4j

# Getting Started

Just run the Application.java on each the server and client instance in your IDE.
Application is running on http://localhost:9080 for server side and on http://localhost:8080 for client side

## Docker
This sample project uses Docker and Docker Compose, The docker-compose file contains services for consul, postgres, pgadmin.
Also, de spring cloud examples are containerized.

### Requirements
[Docker](https://docs.docker.com/install/) and [Docker Compose](https://docs.docker.com/compose/install/) installed.

### Step 1
Use docker-compose to start consul, postgres, pgadmin and spring boot instances.

```sh
docker-compose -f docker-compose.yml up
```

### Step 2
Check the services
- Open http://localhost:8500 - CONSUL
- Open http://localhost:5050 - pgadmin user: pgadmin4@pgadmin.org and pass admin
- Open http://localhost:8080/swagger-ui.html - Client side microservice
- Open http://localhost:9080/swagger-ui.html - Service side microservice, instance 1
- Open http://localhost:1080/swagger-ui.html - Service side microservice, instance 2