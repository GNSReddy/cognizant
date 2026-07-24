package exercise7;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = springtest.SpringTestApplication.class)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByName() {
        User u1 = new User(1L, "Alice");
        User u2 = new User(2L, "Bob");
        userRepository.save(u1);
        userRepository.save(u2);

        List<User> results = userRepository.findByName("Alice");
        assertEquals(1, results.size());
        assertEquals("Alice", results.get(0).getName());
    }
}
