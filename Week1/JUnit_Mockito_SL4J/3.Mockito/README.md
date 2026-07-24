# Mockito Basic Hands-On Exercises

This project covers the basic usage of the Mockito framework to mock dependencies, stub methods, match arguments, handle void methods, check invocation order, and verify exceptions.

## Implemented Exercises
1. **Mocking and Stubbing**: Mocking `ExternalApi` and stubbing `getData()`.
2. **Verifying Interactions**: Assuring that `getData()` was actually called.
3. **Argument Matching**: Matching parameters using `anyInt()`.
4. **Handling Void Methods**: Mocking a void method using `doNothing()`.
5. **Multiple Returns**: Stubbing consecutive calls of `getData()`.
6. **Verifying Order**: Using `InOrder` to verify sequence of mock calls.
7. **Void Methods throwing Exceptions**: Using `doThrow()` on void methods.

## How to Run Tests
Execute the following Maven command:
```bash
mvn clean test
```
