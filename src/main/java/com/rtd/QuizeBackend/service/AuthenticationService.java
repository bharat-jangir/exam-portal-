package com.rtd.QuizeBackend.service;

import com.rtd.QuizeBackend.entities.User;
import com.rtd.QuizeBackend.dto.JwtAuthenticationResponse;
import com.rtd.QuizeBackend.dto.Login;
import com.rtd.QuizeBackend.dto.RefreshTokenRequest;

public interface AuthenticationService {
    User signup(User signUpRequest);
    JwtAuthenticationResponse signIn(Login signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}