package com.library.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * LoggingAspect is a Spring AOP Aspect that logs method execution times.
 * 
 * @Aspect - Marks this class as an AspectJ Aspect containing advice methods.
 *           Spring AOP uses this annotation to identify classes that contain
 *           cross-cutting concerns (logging, security, transactions, etc.).
 * 
 * This aspect intercepts ALL methods in the com.library.service and
 * com.library.repository packages to track their execution times.
 */
@Aspect
public class LoggingAspect {

    /**
     * Around advice that logs execution time for all methods in the service package.
     * 
     * @Around - This is the most powerful advice type. It wraps around the target method,
     *           allowing code to execute BEFORE and AFTER the method, and even deciding
     *           whether the method should execute at all.
     * 
     * Pointcut Expression: "execution(* com.library.service.*.*(..))"
     *   - execution    → Matches method execution join points
     *   - *            → Any return type
     *   - com.library.service  → Target package
     *   - *            → Any class in the package
     *   - *            → Any method name
     *   - (..)         → Any number and type of arguments
     * 
     * @param joinPoint Provides access to the intercepted method's details
     * @return the result of the intercepted method execution
     * @throws Throwable if the intercepted method throws an exception
     */
    @Around("execution(* com.library.service.*.*(..))")
    public Object logServiceExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get the method name being executed
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        System.out.println("\n[AOP LOG] >>> Entering method: " + className + "." + methodName + "()");

        // Record the start time
        long startTime = System.currentTimeMillis();

        // Proceed with the actual method execution
        // joinPoint.proceed() calls the original target method
        Object result = joinPoint.proceed();

        // Record the end time
        long endTime = System.currentTimeMillis();

        // Calculate and log the execution time
        long executionTime = endTime - startTime;
        System.out.println("[AOP LOG] <<< Exiting method: " + className + "." + methodName
                + "() | Execution time: " + executionTime + " ms\n");

        return result;
    }

    /**
     * Around advice that logs execution time for all methods in the repository package.
     * 
     * Pointcut Expression: "execution(* com.library.repository.*.*(..))"
     *   - Matches all methods in any class under com.library.repository
     * 
     * @param joinPoint Provides access to the intercepted method's details
     * @return the result of the intercepted method execution
     * @throws Throwable if the intercepted method throws an exception
     */
    @Around("execution(* com.library.repository.*.*(..))")
    public Object logRepositoryExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get the method name being executed
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        System.out.println("  [AOP LOG] >>> Entering method: " + className + "." + methodName + "()");

        // Record the start time
        long startTime = System.currentTimeMillis();

        // Proceed with the actual method execution
        Object result = joinPoint.proceed();

        // Record the end time
        long endTime = System.currentTimeMillis();

        // Calculate and log the execution time
        long executionTime = endTime - startTime;
        System.out.println("  [AOP LOG] <<< Exiting method: " + className + "." + methodName
                + "() | Execution time: " + executionTime + " ms");

        return result;
    }
}
