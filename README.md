# book-oasis-api
A simple RESTful API for digitalising Mr Dewey's bookshop stock records built with Java and Spring Boot
## What it does
The Book Oasis is a local bookshop that used to track its stock on paper. This API replaces that paper system with five core operations:
- Add a new book
- Retrieve a book by its ID
- Amend (update) an existing book's details
- Remove a book from stock
- View all books, page by page

## Tech stack Used
- Java 21
- Spring Boot 4.1.1 (Spring Web, Spring Data JPA, Validation, Actuator, Swagger)
- H2 in-memory database
- SpringDoc OpenAPI (Swagger UI) for interactive API docs
- JUnit 5 + Mockito for testing
- Maven

## Running the app
**mvnw.cmd spring-boot:run**
The API starts on `http://localhost:8080`

## Trying it out
Once running, open:
http://localhost:8080/swagger-ui/index.html
This gives a full browser-based interface to try every endpoint directly no Postman or command-line tools required. Click "Try it out" on any endpoint to send a real request and see the response.

## Endpoints
| Method | URL | Description |
|--------|-----|--------------|
| POST | `/books` | Add a new book |
| GET | `/books/{id}` | Retrieve a book by ID |
| PUT | `/books/{id}` | Update an existing book |
| DELETE | `/books/{id}` | Delete a book |
| GET | `/books?page=0&size=10` | View books, paginated (supports `sort=title,asc` too) |

### Example: adding a book
```json
POST /books
{
  "title": "Robinhood",
  "author": "Howard Pyle",
  "publicationYear": 1883
}
```
Returns '201 Created' with the saved book, including its generated 'id'.

### Error responses
Errors are returned as clear JSON, not raw stack traces. For example, looking up a book that doesn't exist:
```json
GET /books/9999
404 Not Found
{
  "timestamp": "2026-08-30T12:00:00",
  "status": 404,
  "error": "Book not found with id: 9999"
}
```
Invalid input (e.g. a blank title) returns `400 Bad Request` with a message explaining what's wrong.

## Health check
Basic health monitoring is available via Spring Boot Actuator:
http://localhost:8080/actuator/health

## Running tests
**mvnw.cmd test**
This runs unit tests for the business logic (`BookServiceTest`) and integration tests for the API layer (`BookControllerTest`), covering the core CRUD flow, validation rules, and error handling.
## Test results
All tests pass locally:
\`\`\`
mvn test
Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
\`\`\`

## Design decisions and assumptions
The original brief described Mr Dewey's paper-based process in general terms, so a few implementation decisions were made along the way:
- **Books are identified by a system-generated ID.** The paper system didn't specify how Mr Dewey looks up a book, so each book gets an auto-incrementing ID when added and this is the natural digital equivalent of "finding the right page."
- **H2 in-memory database** is used for simplicity, since no persistence requirement was specified. Data resets when the app restarts. A production deployment would use a persistent database (e.g. PostgreSQL) with proper schema migrations (e.g. Flyway).
- **No authentication** was implemented, as the brief didn't specify any access control needs.
- **Validation** was added as a sensible baseline: title and author are required (and capped at 255 characters, matching typical database column limits), publication year must be a real 4-digit-range year and cannot be in the future. The "not in the future" check is done dynamically at request time (comparing against the current year), rather than hardcoded, so it doesn't need updating every year.
- **Duplicate books are allowed** - adding the same title/author/year twice creates two separate entries with different IDs, representing two distinct physical copies in stock. A natural future enhancement would be tracking a quantity per unique book instead, but that would require defining book "uniqueness" and reworking the add/amend logic, which felt like a bigger change than this exercise called for.
- **Testing approach:** implementation was written first, then covered with unit and integration tests targeting the core behaviour and known edge cases, rather than following strict TDD. Given the requirements were already clearly defined by the brief, this let time go toward covering realistic scenarios rather than a stricter process for its own sake.

## Note on scope
This project implements the backend REST API only, as requested. In a real deployment, Mr Dewey would interact with this through a dedicated frontend application built on top of these endpoints that frontend is outside the scope of this exercise. Swagger UI (above) is included so the API can be fully explored and tested through a browser in the meantime.

In a broader integration context, operations like adding or removing stock could also be published as events to other systems (e.g. via a message queue or Apache Camel route) which is not implemented here, but a natural extension point.
