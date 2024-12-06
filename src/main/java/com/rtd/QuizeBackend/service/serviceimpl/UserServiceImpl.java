package com.rtd.QuizeBackend.service.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rtd.QuizeBackend.repository.UserRepo;
import com.rtd.QuizeBackend.service.UserService;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    public UserRepo userRepo;

      @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            
            @Override
            public UserDetails loadUserByUsername(String username){
                return 
                userRepo.findByUserEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
    
            }
        };

    }


    // @Override
    // public void saveUser(User user) {
    //     User newUser=new User();
    //     newUser.setName(user.getName());
    //     newUser.setEmail(user.getEmail());
    //     newUser.setId(UUID.randomUUID().toString());
    //     newUser.setAddress(user.getAddress());
    //     newUser.setPhoneno(user.getPhoneno());
    //     newUser.setGender(user.getGender());
    //     newUser.setPassword(user.getPassword());
    //     userRepo.save(newUser);
        
    // }
    
    
}
