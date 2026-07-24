package exercise8;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepositoryEx8")
public interface UserRepository extends JpaRepository<User, Long> {
}
