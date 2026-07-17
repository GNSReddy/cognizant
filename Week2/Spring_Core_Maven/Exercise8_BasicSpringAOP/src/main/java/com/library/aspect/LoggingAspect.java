package com.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * LoggingAspect is a Spring AOP Aspect that provides logging
 * cross-cutting concerns for the library management application.
 *
 * This class demonstrates ALL five types of AOP Advice:
 *
 *   1. @Before       - Runs BEFORE the target method executes
 *   2. @After        - Runs AFTER the target method (always, like finally)
 *   3. @AfterReturning - Runs AFTER successful method return
 *   4. @AfterThrowing  - Runs AFTER method throws an exception
 *   5. @Around       - Wraps the method, runs BEFORE and AFTER
 *
 * @Aspect marks this class as containing AOP advice methods.
 * It must also be a Spring bean (declared in applicationContext.xml).
 */
@Aspect
public class LoggingAspect {

    /**
     * POINTCUT DEFINITION
     * ====================
     * A named pointcut that matches all methods in the service package.
     * Defining pointcuts separately allows reuse across multiple advice methods.
     *
     * Pointcut expression: execution(* com.library.service.*.*(..))
     *   - execution    : matches method execution join points
     *   - *            : any return type
     *   - com.library.service : the target package
     *   - *            : any class in that package
     *   - *            : any method name
     *   - (..)         : any number and type of parameters
     */
    @Pointcut("execution(* com.library.service.*.*(..))")
    public void serviceMethods() {
        // Pointcut signature method — body is always empty
    }

    /**
     * POINTCUT DEFINITION for repository methods.
     */
    @Pointcut("execution(* com.library.repository.*.*(..))")
    public void repositoryMethods() {
        // Pointcut signature method — body is always empty
    }

    /**
     * ADVICE TYPE 1: @Before
     * =======================
     * Executes BEFORE the target method runs.
     * Cannot prevent method execution (use @Around for that).
     * Cannot modify return value.
     *
     * JoinPoint provides metadata about the intercepted method:
     *   - getSignature()      : method name and declaring class
     *   - getArgs()           : method arguments
     *   - getTarget()         : the target object
     *
     * @param joinPoint the join point representing the intercepted method
     */
    @Before("serviceMethods()")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        String className  = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args     = joinPoint.getArgs();

        System.out.println("\n[AOP @Before] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("[AOP @Before] Class  : " + className);
        System.out.println("[AOP @Before] Method : " + methodName + "()");
        if (args.length > 0) {
            System.out.println("[AOP @Before] Args   : " + java.util.Arrays.toString(args));
        } else {
            System.out.println("[AOP @Before] Args   : none");
        }
        System.out.println("[AOP @Before] Proceeding to execute method...");
    }

    /**
     * ADVICE TYPE 2: @After
     * ======================
     * Executes AFTER the target method — regardless of whether it
     * returned normally or threw an exception (like a finally block).
     * Cannot access the return value.
     *
     * @param joinPoint the join point representing the intercepted method
     */
    @After("serviceMethods()")
    public void logAfterServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[AOP @After]  Method '" + methodName
                + "()' finished execution (success or exception).");
        System.out.println("[AOP @After]  <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    }

    /**
     * ADVICE TYPE 3: @AfterReturning
     * ================================
     * Executes ONLY when the target method returns successfully (no exception).
     * Can access the return value via the 'returning' attribute.
     *
     * @param joinPoint the join point representing the intercepted method
     * @param result    the actual return value of the method (bound via 'returning')
     */
    @AfterReturning(pointcut = "repositoryMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("  [AOP @AfterReturning] Method '" + methodName
                + "()' returned successfully.");
        System.out.println("  [AOP @AfterReturning] Return value: "
                + (result != null ? result.toString() : "void/null"));
    }

    /**
     * ADVICE TYPE 4: @AfterThrowing
     * ================================
     * Executes ONLY when the target method throws an exception.
     * Can access the thrown exception via the 'throwing' attribute.
     * Does NOT suppress the exception — it still propagates.
     *
     * @param joinPoint the join point representing the intercepted method
     * @param exception the exception thrown by the method
     */
    @AfterThrowing(pointcut = "execution(* com.library.*.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[AOP @AfterThrowing] Method '" + methodName
                + "()' threw exception: " + exception.getMessage());
    }

    /**
     * ADVICE TYPE 5: @Around
     * =======================
     * The most powerful advice type — surrounds the method execution.
     * Controls WHEN (and WHETHER) the target method is called via proceed().
     * Can modify the return value and handle exceptions.
     *
     * Used here to log execution time for all service methods.
     *
     * @param joinPoint ProceedingJoinPoint — extends JoinPoint, adds proceed()
     * @return the return value of the intercepted method
     * @throws Throwable if the intercepted method throws an exception
     */
    @Around("repositoryMethods()")
    public Object logAroundRepositoryMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className  = joinPoint.getTarget().getClass().getSimpleName();

        System.out.println("  [AOP @Around] Entering: " + className + "." + methodName + "()");
        long startTime = System.currentTimeMillis();

        // Proceed with the actual method execution
        Object result = joinPoint.proceed();

        long endTime       = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("  [AOP @Around] Exiting : " + className + "." + methodName
                + "() | Execution time: " + executionTime + " ms");

        return result;
    }
}
