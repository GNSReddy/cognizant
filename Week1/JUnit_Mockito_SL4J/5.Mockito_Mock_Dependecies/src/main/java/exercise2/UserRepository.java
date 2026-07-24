package exercise2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositoryEx2P4")
public interface UserRepository extends JpaRepository<User, Long> {
}
