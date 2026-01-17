package com.aitask.userservice.dto;


public record CreateUserDTO (
     Long userId,
     String username,
     String email,
     String role,
     String name,
     String phoneNumber,
     String bio,
     String profileImage,
     String theme,
     String language,
     String timezone
		) {
	
}
