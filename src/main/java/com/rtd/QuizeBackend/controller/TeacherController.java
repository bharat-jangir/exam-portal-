package com.rtd.QuizeBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rtd.QuizeBackend.entities.User;

@RestController
@RequestMapping(path = "/teacher")
public class TeacherController {
    
    @GetMapping("/students")
    public String createUser(){
        return "all students are here:teacher controller";
    }
    
}
