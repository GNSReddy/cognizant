package exercise1;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LoggingExampleTest {
    @Test
    public void testLoggingExampleOutput() throws Exception {
        new LoggingExample().logMessages();

        File logFile = new File("app.log");
        assertTrue(logFile.exists(), "app.log file should be created");

        List<String> lines = Files.readAllLines(logFile.toPath());
        assertTrue(lines.stream().anyMatch(l -> l.contains("Exercise 1: This is an error message")));
        assertTrue(lines.stream().anyMatch(l -> l.contains("Exercise 1: This is a warning message")));
    }
}
