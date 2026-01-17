package com.aitask.adminservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdminProfileDTO {

    private Long adminId;      // From auth-service (ROLE_ADMIN)

    private String name;
    private String phoneNumber;
    private String profileImage;
    private String bio;

    private String timezone;
    private String language;
    private String theme;

    private String accessLevel; // SUPER_ADMIN, MODERATOR, SUPPORT

	
}
