package com.rtd.QuizeBackend.service.serviceimpl;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rtd.QuizeBackend.entities.User;
import com.rtd.QuizeBackend.constants.Role;
import com.rtd.QuizeBackend.dto.JwtAuthenticationResponse;
import com.rtd.QuizeBackend.dto.Login;
import com.rtd.QuizeBackend.dto.RefreshTokenRequest;
import com.rtd.QuizeBackend.repository.UserRepo;
import com.rtd.QuizeBackend.service.AuthenticationService;
import com.rtd.QuizeBackend.service.JWTService;

// import com.bharat.Lounge.Live.dto.JwtAuthenticationResponse;
// import com.bharat.Lounge.Live.dto.RefreshTokenRequest;
// import com.bharat.Lounge.Live.dto.SignInRequest;
// import com.bharat.Lounge.Live.dto.SignUpRequest;
// import com.bharat.Lounge.Live.entities.Role;
// import com.bharat.Lounge.Live.entities.User;
// import com.bharat.Lounge.Live.repository.UserRepo;
// import com.bharat.Lounge.Live.services.AuthenticationService;
// import com.bharat.Lounge.Live.services.JWTService;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signup(User user) {
        User newUser = new User();
        newUser.setUserEmail(user.getUserEmail());
        newUser.setUserName(user.getUsername());
        newUser.setRole(Role.STUDENT);
        newUser.setUserPhone(user.getUserPhone());
        newUser.setUserPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(newUser);

    }

    public JwtAuthenticationResponse signIn(Login signInRequest) {
        
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

        var user = userRepo.findByUserEmail(signInRequest.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException("user not found"));
        System.out.println(user);
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.getToken());
        var user = userRepo.findByUserEmail(userEmail)
        .orElseThrow(()->new UsernameNotFoundException(userEmail));
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
            var jwt = jwtService.generateToken(user);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
