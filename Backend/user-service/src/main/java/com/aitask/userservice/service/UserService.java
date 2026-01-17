package com.aitask.userservice.service;

import org.springframework.stereotype.Service;

import com.aitask.userservice.dto.CreateUserDTO;
import com.aitask.userservice.entity.UserProfile;
import com.aitask.userservice.repository.AuthServiceClient;
import com.aitask.userservice.repository.UserRepository;



@Service
public class UserService {

	private final AuthServiceClient authClient;
    private final UserRepository repository;

    public UserService(AuthServiceClient authClient, UserRepository repository) {
    	this.authClient = authClient;
        this.repository = repository;
    }
    
    public UserProfile createProfile(UserProfile profile, Long userId) {

        CreateUserDTO authUser = authClient.getUserById(userId);

        System.out.println("Username: " + authUser.username());
        System.out.println("Email: " + authUser.email());

        return repository.save(profile);
    }

    
    
    public UserProfile getProfileByUserId(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));
    }
    

}
