package exercise1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public void logMessages() {
        logger.error("Exercise 1: This is an error message");
        logger.warn("Exercise 1: This is a warning message");
    }
}
