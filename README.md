# Furblur Backend

Furblur is a Spring Boot backend for tracking dog walks. It stores basic walk stats and the recorded GPS coordinates, and exposes a minimal REST API that can be used by a mobile or web client.

## Tech stack
- Java 17
- Spring Boot 3 (Web, Data JPA)
- H2 in-memory database by default; PostgreSQL for production
- Maven for builds and dependency management
- OpenAPI UI via Springdoc at `/swagger-ui/index.html`

## Getting started

### Prerequisites
- Java 17+
- Maven 3.9+ (the included `mvnw` wrapper can also be used)

### Run with the default H2 database
```bash
./mvnw spring-boot:run
```
The application starts on port `8080` and persists data in an in-memory H2 database. The H2 console is available at `/h2-console` when running locally.

### Run with PostgreSQL (production profile)
Provide the database connection details and enable the `prod` profile:
```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:<port>/<db> \
SPRING_DATASOURCE_USERNAME=<username> \
SPRING_DATASOURCE_PASSWORD=<password> \
SPRING_PROFILES_ACTIVE=prod \
./mvnw spring-boot:run
```

## API

### Create a walk
`POST /walks`
```json
{
  "date": "2024-02-19",
  "startTime": "2024-02-19T08:30:00",
  "endTime": "2024-02-19T09:05:00",
  "distanceKm": 3.2,
  "averagePace": 5.5,
  "durationFormatted": "00:35:00",
  "energyLevel": "High",
  "happiness": "Very happy",
  "behaviour": "Calm",
  "coordinates": [
    { "latitude": 40.7128, "longitude": -74.0060 },
    { "latitude": 40.7130, "longitude": -74.0055 }
  ]
}
```
- Automatically persists coordinates linked to the walk.
- Returns the saved walk with database-generated IDs.

### List walks
`GET /walks`
- Returns all walks in descending order by ID (newest first), including their coordinates.

## Development tips
- Entities are in `src/main/java/com/furblur/` and use JPA annotations.
- Default configuration is in `src/main/resources/application.properties`; production overrides live in `application-prod.properties`.
- Run the test suite with:
```bash
./mvnw test
```
