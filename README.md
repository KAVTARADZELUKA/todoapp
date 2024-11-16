# Todo List Application

This is a RESTful API for managing a todo list, built with Spring Boot, Hibernate, and MySQL.

## Features

- Create, read, update, delete todo items.
- Each item has a title and description.

## Prerequisites

- Java 17
- MySQL
- Maven

## Configuration

Configure your database credentials in `src/main/resources/application.properties`.

## Running the Application

1. Build the project: `mvn clean install`
2. Run the application: `mvn spring-boot:run`

## Testing

Run unit tests with: `mvn test`

## API Endpoints

- `GET /api/todos` - Get all todo items
- `POST /api/todos` - Create a new todo item
- `GET /api/todos/{id}` - Get a specific todo item by ID
- `PUT /api/todos/{id}` - Update a todo item
- `DELETE /api/todos/{id}` - Delete a todo item
