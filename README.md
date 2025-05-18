# API Automation Testing Project

This repository contains automated API tests for the WhiteSmokeHouse API using RestAssured and TestNG frameworks.

## Project Structure

```
├── src
│   └── test
│       ├── java
│       │   └── restassured
│       │       ├── base
│       │       │   └── BaseTest.java
│       │       └── tests
│       │           ├── DepartmentTests.java
│       │           ├── ObjectTests.java
│       │           └── UserTests.java
│       └── resources
│           └── testng_suite.xml
└── pom.xml
```

## Prerequisites

- Java JDK 17
- Maven
- Git

## Setup Instructions

1. Clone the repository:

```bash
git clone https://github.com/syahriarreza/qa-auto-task-day-02.git
```

2. Navigate to the project directory:

```bash
cd qa-auto-task-day-02
```

3. Install dependencies:

```bash
mvn clean install -DskipTests
```

## Running the Tests

### Run all tests

To run all API tests defined in the TestNG suite:

```bash
mvn test
```

### Run specific test classes

To run tests from a specific class:

```bash
mvn test -Dtest=UserTests
```

```bash
mvn test -Dtest=DepartmentTests
```

```bash
mvn test -Dtest=ObjectTests
```

## Test Suite Overview

The test suite is configured in `src/test/resources/testng_suite.xml` and includes the following test classes:

1. **UserTests**: Tests user registration functionality
2. **DepartmentTests**: Tests department API endpoints
3. **ObjectTests**: Tests object CRUD operations (Create, Read, Update, Delete)

## Authentication

The tests use token-based authentication. The base test class (`BaseTest.java`) handles authentication by:
- Making a login request to obtain an authentication token
- Storing the token for use in subsequent API requests

## Test Descriptions

### User Tests
- `testRegister`: Tests user registration functionality with validation for already registered users

### Department Tests
- `testGetAllDepartment`: Tests retrieving all departments

### Object Tests
- `testAddObject`: Creates a new object (runs as setup)
- `testGetListAllObjects`: Tests retrieving all objects
- `testSingleObject`: Tests retrieving a specific object by ID
- `testListOfObjectsByIds`: Tests retrieving objects by specific IDs
- `testUpdateObject`: Tests updating an object
- `testPartiallyUpdateObject`: Tests partial update of an object
- `testDeleteObject`: Tests deleting an object (runs as cleanup)

## Dependencies

- TestNG 7.11.0: Testing framework
- Rest Assured 5.5.1: API testing library

## Notes

- The test suite uses a predefined user account for authentication

## Troubleshooting

If you encounter authentication issues:
1. Check if the credentials in `BaseTest.java` are valid
2. Verify the API base URL is correct
3. Check if the API endpoints are accessible from your network

For other issues, check the test output for detailed error messages.