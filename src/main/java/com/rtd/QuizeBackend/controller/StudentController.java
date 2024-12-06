package com.rtd.QuizeBackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/student")
public class StudentController {

    @GetMapping("/tests")
    public String allTests(){
        return "all tersts are here: student controller";
    }
    
}
