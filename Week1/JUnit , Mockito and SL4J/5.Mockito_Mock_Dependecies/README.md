# Mocking Dependencies in Spring Boot Tests using Mockito

This project demonstrates dependency mocking at different layers using Mockito in a Spring Boot application.

## Implemented Exercises
1. **Mocking Service in Controller Test**: Mocking `UserService` in `UserControllerTest` via `@WebMvcTest` and `@MockBean`.
2. **Mocking Repository in Service Test**: Mocking `UserRepository` in `UserServiceTest` using standard Mockito `@Mock` and `@InjectMocks`.
3. **Mocking Service in Integration Test**: Using `@SpringBootTest` and `@AutoConfigureMockMvc` with a mocked `@MockBean` service dependency.

## How to Run Tests
Execute the following Maven command:
```bash
mvn clean test
```
