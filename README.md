# Library Management System - Spring Boot API
This project implements a Library Management System API using Spring Boot. It allows librarians to manage books, patrons, and borrowing records.

## Features:

- [X] CRUD operations for Books, Patrons, and Borrowing Records
- [X] RESTful API Endpoints for all functionalities
- [X] Data persistence with a chosen database (e.g., H2, MySQL, PostgreSQL)
- [X] Input validation for API requests
- [X] Proper error handling with appropriate HTTP status codes and messages
- [X] Declarative transaction management for data integrity


## Bonus Features (Implemented):

- [X] **Security**: Implement basic authentication using JWT with Roles and Permissions based authorization to protect the API endpoints.
- [X] **Aspects**: Logging method calls, exceptions, and performance metrics using AOP.
- [X] **Caching**: Caching frequently accessed data (e.g., book details) for improved performance.
- [X] **CI/CD**: Implement simple pipeline using GitHub Actions to deploy API to Docker Image for every push to _main_ branch.

## Running the Application:

### 1. Prerequisites:
- Java 17+
- Maven

### 2. Clone the project:
``` Bash
git clone https://github.com/3umarG/Maids-Test-Quiz.git
```

### 3. Run Dokcer Compose
``` Bash
docker-compose up -d
```

### 4. Build and Run
``` Bash
mvn clean install
mvn spring-boot:run
```


## Documentation:

All API endpoints are documented using Spring REST Docs. You can access the documentation at [http://localhost:7777/swagger-ui.html](http://localhost:7777/swagger-ui/index.html)


## Testing
Unit tests covering functionalities are located under src/test/java.


## Evaluation:

This project aims to showcase proficiency in building a robust API using Spring Boot. It demonstrates:

- CRUD operations with data persistence
- RESTful API design
- Error handling and validation
- Transaction management
- Bonus features for improved functionality and security

### This project is well-suited for evaluating skills in:

- Spring Boot
- Spring Data JPA
- RESTful API development
- Unit testing
- Bonus: Security, AOP, caching, and CI/CD.

Feel free to explore the codebase and experiment with additional features to further enhance the Library Management System.
