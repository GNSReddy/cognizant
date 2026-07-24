package exercise7;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("userRepositoryEx7")
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}
