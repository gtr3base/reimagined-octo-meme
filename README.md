# Kafka Microservices Demo

A simple event-driven microservices application built with **Java, Spring Boot, Apache Kafka, and Docker**.

The project demonstrates asynchronous communication between two independent Spring Boot services using Apache Kafka.

## Architecture

The application consists of two microservices:

- **OrderService** — creates orders and publishes order-created events to Kafka.
- **NotificationService** — consumes order-created events and logs order information to the console.

### Event flow

```text
       HTTP POST
           |
           v
    +---------------+
    |  OrderService |
    |   Port 8081   |
    +---------------+
           |
           | Kafka Producer
           v
    +---------------+
    | Apache Kafka  |
    |  Port 9092    |
    |               |
    | Topic:        |
    | order-created |
    +---------------+
           |
           | Kafka Consumer
           v
    +---------------------+
    | NotificationService |
    |     Port 8082       |
    +---------------------+
           |
           v
      Console Logs
```

## Technologies

- Java 21
- Spring Boot 4.1.2
- Spring Kafka
- Apache Kafka 4.1.0
- Maven
- Docker & Docker Compose
- Jackson
- REST API

## Project Structure

```text
kafka-demo/
│
├── docker-compose.yml
│
├── order-service/
│   ├── pom.xml
│   └── src/
│
└── notification-service/
    ├── pom.xml
    └── src/
```

## Getting Started

### Prerequisites

Make sure you have installed:

- JDK 21+
- Maven
- Docker Desktop
- Git

### 1. Clone the repository

```bash
git clone https://github.com/gtr3base/reimagined-octo-meme
cd kafka-demo
```

### 2. Start Apache Kafka

Run Kafka using Docker Compose:

```bash
docker compose up -d
```

Check the container status:

```bash
docker compose ps
```

View Kafka logs:

```bash
docker compose logs -f kafka
```

### 3. Start NotificationService

Open the `notification-service` directory and run:

```bash
mvn spring-boot:run
```

The service starts on port `8082` and subscribes to the `order-created` topic.

### 4. Start OrderService

Open another terminal in the `order-service` directory:

```bash
mvn spring-boot:run
```

The service starts on port `8081`.

## API Usage

### Create an order

**Endpoint:**

```http
POST http://localhost:8081/api/orders
```

**Query Parameters:**

| Parameter | Type | Description |
|---|---|---|
| product | String | Product name |
| quantity | Integer | Order quantity |

**Example request:**

```http
POST http://localhost:8081/api/orders?product=iPhone%2017&quantity=2
```

**Example response:**

```json
{
  "orderId": "e00c8d5e-7d4b-4d4d-9e1a-2c3a12345678",
  "product": "iPhone 17",
  "quantity": 2,
  "createdAt": "2026-11-01T16:30:00"
}
```

## Kafka Message

When an order is created, OrderService publishes a JSON event to the `order-created` topic.

Example event:

```json
{
  "orderId": "e00c8d5e-7d4b-4d4d-9e1a-2c3a12345678",
  "product": "iPhone 17",
  "quantity": 2,
  "createdAt": "2026-11-01T16:30:00"
}
```

NotificationService consumes the event using the `notification-group` consumer group.

Example console output:

```text
========== NEW ORDER ==========
Order ID: e00c8d5e-7d4b-4d4d-9e1a-2c3a12345678
Product: iPhone 17
Quantity: 2
Created at: 2026-11-01T16:30
===============================
```

## Kafka Configuration

| Property | Value |
|---|---|
| Bootstrap server | localhost:9092 |
| Topic | order-created |
| Consumer Group | notification-group |
| Key Serializer | StringSerializer |
| Value Serializer | StringSerializer |
| Key Deserializer | StringDeserializer |
| Value Deserializer | StringDeserializer |

## Docker Commands

Start Kafka:

```bash
docker compose up -d
```

Stop Kafka:

```bash
docker compose down
```

View logs:

```bash
docker compose logs -f kafka
```

Check running containers:

```bash
docker compose ps
```

## Key Concepts Demonstrated

- Event-driven architecture
- Asynchronous inter-service communication
- Kafka Producers and Consumers
- Kafka Topics
- Consumer Groups
- Message Serialization and Deserialization
- Spring Kafka integration
- Docker-based infrastructure

## Future Improvements

- Containerize both Spring Boot services.
- Add PostgreSQL persistence to OrderService.
- Implement Kafka error handling and retry mechanisms.
- Introduce Dead Letter Topics (DLT).
- Explore Kafka partitions and consumer scaling.
- Implement idempotent message processing.

---

**Purpose:** Learning and demonstrating Apache Kafka integration with Spring Boot microservices.