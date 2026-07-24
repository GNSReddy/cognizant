# Mockito Advanced Hands-On Exercises

This project demonstrates advanced testing scenarios using Mockito, including database mocking, REST client mocking, File I/O mocking, network simulation, and consecutive return stubs.

## Implemented Exercises
1. **Mocking Databases and Repositories**: Mocking `Repository` dependency in `ServiceTest`.
2. **Mocking External RESTful Services**: Stubbing HTTP REST responses in `ApiServiceTest`.
3. **Mocking File I/O**: Intercepting and validating file read and write tasks in `FileServiceTest`.
4. **Mocking Network Interactions**: Simulating sockets/connections in `NetworkServiceTest`.
5. **Mocking Multiple Return Values**: Returning consecutive stubs on repository calls in `MultiReturnServiceTest`.

## How to Run Tests
Execute the following Maven command:
```bash
mvn clean test
```
