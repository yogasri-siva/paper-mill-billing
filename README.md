# Paper Mill Billing System

## Overview

This project implements a simple billing system for a paper mill.  It
exposes a REST API to compute and store invoices based on the paper’s
GSM (grams per square metre), BF (burst factor), width and quantity.  A
single‑page front‑end uses soft pink and white styling to collect input
and display the calculated invoice.

The pricing logic is: GSM selects a
base price band, BF adds surcharges in 2‑point increments, and a 12 %
GST is applied on the subtotal.  The system persists orders in an
in‑memory H2 database and demonstrates how events could be published to
Kafka.

## Tech Stack

- **Java 17** & **Spring Boot 3** for the server, exposing REST
  endpoints and using Spring Data JPA for persistence.
- **H2** in‑memory database for development and testing.
- **OpenAPI / Swagger UI** via springdoc to document the API.
- **HTML, CSS & Vanilla JS** front‑end served from Spring’s static
  resources.
- **Docker** with a multi‑stage build and **docker‑compose** for
  containerisation.
- (Optional) **Kafka** mock publisher to illustrate event emission.

## Setup & Run Instructions

### Prerequisites

- Java 17 and Maven installed for local runs.
- Docker and docker‑compose if you wish to run via containers.

### Running Locally

1. Navigate to the project directory:

   ```bash
   cd paper-mill-billing
   ```

2. Build and start the application:

   ```bash
   mvn spring-boot:run
   ```

3. Open your browser and navigate to [`http://localhost:8080`](http://localhost:8080)
   to load the front‑end.  The API documentation is available at
   [`http://localhost:8080/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html).

### Running with Docker

1. Ensure Docker is installed.
2. From the project root run:

   ```bash
   docker compose up --build
   ```

3. Access the UI at [`http://localhost:8080`](http://localhost:8080).
   If port 8080 is already used on your host you can change the
   mapping in `docker-compose.yml` (e.g. `8085:8080`).

### H2 Console

The in‑memory database console is available at
`http://localhost:8080/h2`.  Use JDBC URL `jdbc:h2:mem:papermill`, user
`sa` and no password.

## API Endpoints

| Method | Path             | Description                      |
| ------ | ---------------- | -------------------------------- |
| POST   | `/api/orders`    | Create a new order and bill it   |
| GET    | `/api/orders`    | List all orders                  |
| GET    | `/api/orders/{id}` | Get a specific order by ID       |
| DELETE | `/api/orders/{id}` | Delete an order by ID            |

### Sample Payload (POST `/api/orders`)

```json
{
  "quantity": 10,
  "width": 36,
  "gsm": 120,
  "bf": 18,
  "cobb": 30
}
```

### Sample Response

```json
{
  "orderId": 1,
  "pricePerUnit": 42500.00,
  "subtotal": 425000.00,
  "gst": 51000.00,
  "total": 476000.00
}
```
### Notes:
- If you plan to run Kafka, uncomment the broker configuration in
  `docker-compose.yml` and supply a KafkaTemplate bean.  Otherwise, the
  `OrderEventPublisher` logs events for demonstration.
