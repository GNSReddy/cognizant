package exercise5;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositoryEx5")
public interface UserRepository extends JpaRepository<User, Long> {
}
