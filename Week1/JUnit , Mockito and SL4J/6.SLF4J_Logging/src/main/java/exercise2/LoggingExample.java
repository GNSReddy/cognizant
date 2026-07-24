package exercise2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public void logMessages(String user, int id) {
        logger.info("Exercise 2: User {} logged in with ID {}", user, id);
    }
}
