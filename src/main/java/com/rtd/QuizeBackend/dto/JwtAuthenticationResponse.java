package com.rtd.QuizeBackend.dto;

import com.rtd.QuizeBackend.constants.Role;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
    private Role role;


}