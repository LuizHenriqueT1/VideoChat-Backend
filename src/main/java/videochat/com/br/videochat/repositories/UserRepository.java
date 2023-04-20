package videochat.com.br.videochat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import videochat.com.br.videochat.entities.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmailId(final String emailId);

    Optional<User> findByEmailId(String emailId);
}
