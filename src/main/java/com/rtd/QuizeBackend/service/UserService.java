package com.rtd.QuizeBackend.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.rtd.QuizeBackend.entities.User;

@Service
public interface UserService {
     UserDetailsService userDetailsService();

    // public void saveUser(User user);
}
