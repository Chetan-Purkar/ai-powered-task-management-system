package com.aitask.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtUserPrincipal {

    private Long userId;
    private String username;
    private String role;
}
