package com.aitask.userservice.repository;

import com.aitask.userservice.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserProfile, Long> {

}
