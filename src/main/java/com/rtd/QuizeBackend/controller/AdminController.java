package com.rtd.QuizeBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {


    @GetMapping("/teachers")
    public String allTeachers(){
        return " all teachers are here: Admin Controller";
    }
    
}
