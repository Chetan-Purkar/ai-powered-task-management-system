package com.aitask.userservice.dto;

import com.aitask.userservice.entity.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMeDTO {
    private Long userId;
    private String username;
    private String role;
    private UserProfile profile;
}
