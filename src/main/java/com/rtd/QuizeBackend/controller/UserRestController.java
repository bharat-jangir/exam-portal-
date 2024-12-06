package com.rtd.QuizeBackend.controller;

import java.net.http.HttpResponse;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rtd.QuizeBackend.constants.Role;
import com.rtd.QuizeBackend.dto.JwtAuthenticationResponse;
import com.rtd.QuizeBackend.dto.Login;
import com.rtd.QuizeBackend.dto.RegisterUserDto;
import com.rtd.QuizeBackend.entities.User;
import com.rtd.QuizeBackend.repository.UserRepo;
import com.rtd.QuizeBackend.service.JWTService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@CrossOrigin
public class UserRestController {
    
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


      @PostMapping("/user/login")
      public JwtAuthenticationResponse signIn(@RequestBody Login signInRequest) {
        
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
        jwtAuthenticationResponse.setRole(user.getRole());
        return jwtAuthenticationResponse;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<RegisterUserDto> signUp(@RequestBody RegisterUserDto registerRequest) {
    
        // Create a new User entity from the request
        User user = User.builder()
            .userEmail(registerRequest.getEmail())
            .userName(registerRequest.getFullName())
            .userPhone(registerRequest.getPhone())
            .role(Role.STUDENT)
            .userPassword(passwordEncoder.encode(registerRequest.getPassword()))
            .build();
    
        // Save the user to the database
        User newUser = userRepo.save(user);
    
        // Build the response DTO
        RegisterUserDto response = new RegisterUserDto();
        response.setEmail(newUser.getUserEmail());
        response.setFullName(newUser.getUsername());
        response.setPhone(newUser.getUserPhone());
    
        // Return the response DTO
        return ResponseEntity.ok(response);
    }
    
}
