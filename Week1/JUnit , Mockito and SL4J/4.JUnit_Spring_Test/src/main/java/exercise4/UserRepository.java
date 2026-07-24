package exercise4;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositoryEx4")
public interface UserRepository extends JpaRepository<User, Long> {
}
