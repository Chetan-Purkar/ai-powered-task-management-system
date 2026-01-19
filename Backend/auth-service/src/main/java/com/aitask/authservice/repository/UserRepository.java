package com.aitask.authservice.repository;

import com.aitask.authservice.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByEmail(String email);
    Optional<UserAuth> findByUsernameOrEmail(String identifier, String identifier2);
    boolean existsByUsername(String username);

}
