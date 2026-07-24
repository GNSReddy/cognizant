package exercise3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingExample {
    private static final Logger logger = LoggerFactory.getLogger(LoggingExample.class);

    public void logMessages() {
        logger.info("Exercise 3: Logging to both console and file");
    }
}
