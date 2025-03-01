# IETT System - Full-Stack Application

Full-Stack Developer

## Project Structure

The project consists of two main components:

- **Backend (Java Spring Boot)**
- **Frontend (React with TypeScript)**

## Design Choices and Approach

### Backend


### Frontend


## Assumptions Made

1. The IETT SOAP services might not always be available, so data is cached in the database.
2. User interface is prioritized for simplicity and ease of use.
3. The application is containerized for easy deployment and scalability.

## How to Run the Application

### Prerequisites

- Docker and Docker Compose installed
- Git installed

### Steps to Run

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd iett-system
   ```

2. Start the application using Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. Access the application:
 
   - Backend API: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html

## API Endpoints

### Bus Endpoints

- `GET /api/buses` - Get all buses
- `GET /api/buses/search` - Search buses with filters
- `GET /api/buses/filter` - Filter buses with a single search term
- `GET /api/buses/{id}` - Get a specific bus by ID
- `POST /api/buses` - Create a new bus
- `PUT /api/buses/{id}` - Update an existing bus
- `DELETE /api/buses/{id}` - Delete a bus

### Garage Endpoints

- `GET /api/garages` - Get all garages
- `GET /api/garages/search` - Search garages with filters
- `GET /api/garages/filter` - Filter garages with a single search term
- `GET /api/garages/{id}` - Get a specific garage by ID
- `POST /api/garages` - Create a new garage
- `PUT /api/garages/{id}` - Update an existing garage
- `DELETE /api/garages/{id}` - Delete a garage

## Technologies Used

### Backend

- Java 17
- Spring Boot 3.4.3
- Spring Data JPA
- Spring Web Services
- PostgreSQL
- Swagger/OpenAPI

### Frontend


### DevOps
