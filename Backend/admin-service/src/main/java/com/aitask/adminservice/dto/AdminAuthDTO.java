package com.aitask.adminservice.dto;

import com.aitask.adminservice.model.AdminProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminAuthDTO {
	
	private Long userId;
    private String username;
    private String role;
    private AdminProfile profile;

}
