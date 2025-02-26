# Spring Boot CQRS with Kafka

A microservices demonstration project implementing the CQRS (Command Query Responsibility Segregation) pattern using Spring Boot and Apache Kafka.

From Udemy course: [Java Microservices: CQRS & Event Sourcing with Kafka](https://www.udemy.com/course/java-microservices-cqrs-event-sourcing-with-kafka)

## Architecture Overview

This project demonstrates a CQRS architecture with:

- **Account Command Service**: Handles write operations (commands)
- **Account Query Service**: Handles read operations (queries)
- **Event Sourcing**: Using Apache Kafka as the event store and message broker

## Features

- Separated read and write models
- Event-driven architecture
- Asynchronous communication between services
- Event sourcing pattern implementation

## Technology Stack

- **Java 21**
- **Spring Boot 3.x**
- **Apache Kafka**
- **MySQL**
- **MongoDB**
- **Docker & Docker Compose**

## Project Structure

```
springboot-cqrs-kafka/
├── account-cmd-service/           # Handles commands (write operations)
├── account-common/                # Account shared code
├── account-query-service/         # Handles queries (read operations)
├── cqrs-core/                     # CQRS shared code
└── docker/                        # Docker configuration files

```

## Getting Started

### Prerequisites

- Java 21+
- Gradle 3.8+
- Docker and Docker Compose

### Running Locally

1. Start the infrastructure (Kafka, Zookeeper, MySQL, MongoDB):
   ```bash
   docker compose -f docker/docker-compose.yml up -d
   ```

2. Build the project:
   ```bash
   ./gradlew clean build
   ```

3. Run the account command service:
   ```bash
   # Run command service
   ./gradlew :account-cmd-service:bootRun
   ```
4. Run the account query service:
   ```bash 
   # Run query service
   ./gradlew :account-query-service:bootRun
   ```

### Test API Endpoints

1. Create Bank Account:
   ```bash
    # Save id generated to be used in the next steps
    curl --request POST \
      --url http://localhost:7001/api/v1/open-bank-account \
      --header 'content-type: application/json' \
      --data '{
      "accountHolder":"John Doe",
      "accountType": "CHECKING",
      "balance": 1500.00
      }'
   ```
   
2. Deposit funds:
   ```bash
    curl --request PUT \
      --url http://localhost:7001/api/v1/deposit-funds/<bank_account_id_goes_here> \
      --header 'content-type: application/json' \
      --data '{
      "amount": 150
      }'  
   ```
   
3. Confirm deposit funds:
   ```bash
   curl --request GET \
    --url http://localhost:7002/api/v1/bank-account-lookup/<bank_account_id_goes_here>
   ```
   
4. Withdraw funds:
   ```bash
    curl --request PUT \
    --url http://localhost:7001/api/v1/withdraw-funds/<bank_account_id_goes_here> \
    --header 'content-type: application/json' \
    --data '{"amount":49}'
   ```
    
5. Confirm withdraw funds:
   ```bash
   curl --request GET \
    --url http://localhost:7002/api/v1/bank-account-lookup/<bank_account_id_goes_here>
   ```
   
6. Delete all MySQL rows:
   ```sql
      truncate table bank_account;
   ```
   
7. Check bank account no longer exists:
   ```bash
   curl --request GET \
    --url http://localhost:7002/api/v1/bank-account-lookup/<bank_account_id_goes_here>
   ```
   
8. Restore database:
   ```bash
   curl --request POST \
   --url http://localhost:7001/api/v1/restore-read-db
   ```

9. Check bank account exists and balance is correct:
   ```bash
   curl --request GET \
    --url http://localhost:7002/api/v1/bank-account-lookup/<bank_account_id_goes_here>
   ```   
