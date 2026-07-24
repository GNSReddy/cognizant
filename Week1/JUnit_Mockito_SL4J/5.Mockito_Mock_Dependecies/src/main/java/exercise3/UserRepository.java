package exercise3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositoryEx3P4")
public interface UserRepository extends JpaRepository<User, Long> {
}
