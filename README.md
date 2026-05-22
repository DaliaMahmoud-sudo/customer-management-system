# Customer Management Application

## Overview

This project consists of two applications:

1. Spring Boot Backend REST API
2. Java Swing Desktop Client

The desktop application communicates with the backend using HTTP REST APIs.
The desktop application does NOT connect directly to the database.

---

# Technologies Used

## Backend
- Java 17
- Spring Boot
- Spring Data JPA
- SQL Server
- Maven

## Desktop Application
- Java Swing
- OkHttp
- Gson
- Maven

---

# Features

## Backend
- Create Customer
- Get All Customers
- Update Customer
- Delete Customer
- SQL Server integration
- REST API

## Desktop Application
- Customer table
- Add customer
- Update customer
- Delete customer
- Real-time search/filter
- Form validation
- Loading indicator
- Error handling

---

# Project Structure

## Backend
- Controller
- Service
- Repository
- Entity

## Desktop Client
- UI
- Service
- Model

---

# Database

The database table is automatically generated using Spring Data JPA.

No manual SQL creation is required.

---

# How To Run Backend

1. Open backend project
2. Configure database connection in:

```properties
application.properties
```

3. Run:

```bash
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

---

# How To Run Desktop Application

1. Make sure backend is running
2. Open desktop project
3. Run Main.java

---

# API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | /customers | Get all customers |
| POST | /customers | Create customer |
| PUT | /customers/{id} | Update customer |
| DELETE | /customers/{id} | Delete customer |

---

# Validation

Both client-side and server-side validation are implemented.

Examples:
- Required fields
- Email format validation
- Phone validation

---

# Bonus Features

- Real-time filtering/search
- Loading indicator
- Confirmation dialogs
- Error handling
- Improved UI
