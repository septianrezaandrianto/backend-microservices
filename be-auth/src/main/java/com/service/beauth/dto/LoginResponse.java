package com.service.beauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String userId;
    private String username;
    private String accessToken;
    private String tokenType;
    private int expiresIn;
    private String role;

}
