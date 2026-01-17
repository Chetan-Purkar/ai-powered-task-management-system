package com.aitask.authservice.repository;

import com.aitask.authservice.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByUsername(String username);
    Optional<UserAuth> findByEmail(String email);
    boolean existsByUsername(String username);

}
