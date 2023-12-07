# Java Internship Spring Boot Project

## Introduction

This Java Internship Spring Boot Project is a simple project showcasing CRUD operations on a Product entity using PostgreSQL as the database. The application is designed to manage product information through RESTful API endpoints, providing functionality for creating, retrieving, updating, and deleting products. It includes features such as Java Streams for data processing, exception handling, pagination, and sorting.

## Features

- **CRUD Operations:** Perform Create, Read, Update, and Delete operations on product entities.
- **Database Connectivity:** Connects to a PostgreSQL database and defines a schema for the Product table.
- **Data Transfer Objects (DTOs):** Utilizes DTOs for effective data transfer between the client and server.
- **MapStruct Mapper:** Implements a MapStruct mapper interface for converting between `Product` entities and `ProductDTOs`.
- **Java Streams:** Includes an endpoint to filter products by category using Java Streams.
- **Unit Testing:** Provides comprehensive unit tests for all API endpoints using the MockMvc framework and all services using mockito and junit.
- **Exception Handling:** Implements a global exception handler using `@ControllerAdvice` for robust error handling.
- **Pagination and Sorting:** Enhances the `GET /api/products` endpoint to support query parameters for pagination and sorting.

