package exercise3;

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
        assertTrue(logFile.exists());

        List<String> lines = Files.readAllLines(logFile.toPath());
        assertTrue(lines.stream().anyMatch(l -> l.contains("Exercise 3: Logging to both console and file")));
    }
}
