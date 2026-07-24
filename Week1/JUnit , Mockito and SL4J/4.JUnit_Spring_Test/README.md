# Spring Boot and JUnit 5 Testing Hands-On

This project showcases unit and integration testing capabilities using JUnit 5, Mockito, and Spring Boot Test frameworks.

## Implemented Exercises
1. **Basic Unit Test**: Standard test for `CalculatorService.add()`.
2. **Mocking Repository**: Stubbing `UserRepository` in `UserServiceTest`.
3. **MockMvc GET Test**: MockMvc verification for GET `/users/{id}`.
4. **Integration Test**: E2E integration test checking the full flow using an in-memory H2 database.
5. **MockMvc POST Test**: MockMvc verification for POST `/users`.
6. **Service Exception Test**: Verifying service throws `NoSuchElementException` on missing user.
7. **Custom Repository Query Test**: Validating custom `findByName` JPA query using `@DataJpaTest`.
8. **Controller Exception Test**: Verifying `@ControllerAdvice` Exception handler mapping.
9. **Parameterized Test**: Running csv parameter inputs on `CalculatorService` tests.

## How to Run Tests
Execute the following Maven command:
```bash
mvn clean test
```
