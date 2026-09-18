# Delivery Tracker

REST API for tracking deliveries registration, lookup, and status control, with business-rule-validated status transitions.

This is a study project built in stages, on purpose: the goal is to show the application's growth over time, starting from a simple in-memory version and evolving toward a full API with real persistence.

## Stack

- Java 21
- Spring Boot 4.1.1 (Spring Web, Validation, DevTools)
- Maven
- Bean Validation (Jakarta)

## Architecture

```
model/       → domain entities (Delivery, DeliveryStatus)
dto/         → request/response objects, decoupled from the entity
repository/  → interface + in-memory implementation (easy to swap for JPA later)
service/     → business rules (validation, status transitions)
exception/   → domain exceptions + global handler (@RestControllerAdvice)
controller/  → REST endpoints
```

The `service` layer knows nothing about HTTP or persistence details that's what allows the `repository` to be swapped for a database-backed implementation (v3) without touching the business logic.

## Business rules

Possible statuses: `PENDING`, `IN_TRANSIT`, `DELIVERED`, `CANCELLED`

Allowed transitions:
- `PENDING` → `IN_TRANSIT` or `CANCELLED`
- `IN_TRANSIT` → `DELIVERED` or `CANCELLED`
- `DELIVERED` and `CANCELLED` are final states — no transition out of them is allowed

Skipping steps is not allowed (e.g. `PENDING` straight to `DELIVERED`).

## Endpoints

| Method | Route | Description |
|---|---|---|
| POST | `/api/deliveries` | Register a new delivery |
| GET | `/api/deliveries` | List all deliveries (accepts `?status=` as a filter) |
| GET | `/api/deliveries/{id}` | Fetch a delivery by id |
| PATCH | `/api/deliveries/{id}/status` | Update a delivery's status |
| DELETE | `/api/deliveries/{id}` | Remove a delivery |

All examples below were run and verified against a live instance of the API.

### Example — create a delivery

```bash
curl -X POST http://localhost:8080/api/deliveries \
  -H "Content-Type: application/json" \
  -d '{"recipient":"Maria Silva","address":"Rua das Flores, 123","expectedDate":"2026-09-25"}'
```

Response (`201 Created`):
```json
{"id":1,"recipient":"Maria Silva","address":"Rua das Flores, 123","status":"PENDING","createdDate":"2026-09-18","expectedDate":"2026-09-25","deliveredDate":null,"late":false}
```

### Example — valid status update

```bash
curl -X PATCH http://localhost:8080/api/deliveries/1/status \
  -H "Content-Type: application/json" \
  -d '{"status":"IN_TRANSIT"}'
```

Response (`200 OK`):
```json
{"id":1,"recipient":"Maria Silva","address":"Rua das Flores, 123","status":"IN_TRANSIT","createdDate":"2026-09-18","expectedDate":"2026-09-25","deliveredDate":null,"late":false}
```

### Example — invalid status update (skipping a step)

```bash
curl -X PATCH http://localhost:8080/api/deliveries/1/status \
  -H "Content-Type: application/json" \
  -d '{"status":"DELIVERED"}'
```

Response (`409 Conflict`):
```json
{"timestamp":"2026-09-18T19:02:33.543778236","status":409,"error":"Conflict","message":"Cannot change delivery status from PENDING to DELIVERED","details":[]}
```

### Example — filter by status

```bash
curl "http://localhost:8080/api/deliveries?status=IN_TRANSIT"
```

### Example — delete

```bash
curl -X DELETE http://localhost:8080/api/deliveries/1 -i
```

Response: `204 No Content`

## Running the project

```bash
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`.

> **Note:** current persistence is in-memory. Restarting the application including DevTools' auto-restart on file save clears all data.

## Roadmap

- [x] v1 — Basic CRUD in memory via terminal (layered architecture study)
- [x] v2 — REST API with Spring Boot, DTOs, and centralized error handling
- [ ] v3 — Real persistence with Spring Data JPA + database
- [ ] v4 — Authentication / multi-user support
- [ ] v5 — Notifications and status change history
