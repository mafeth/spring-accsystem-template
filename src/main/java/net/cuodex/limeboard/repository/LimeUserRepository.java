package net.cuodex.limeboard.repository;

import net.cuodex.limeboard.entity.LimeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface LimeUserRepository extends JpaRepository<LimeUser, Serializable> {
    Optional<LimeUser> findByEmail(String email);
    Optional<LimeUser> findByUsernameOrEmail(String username, String email);
    Optional<LimeUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}