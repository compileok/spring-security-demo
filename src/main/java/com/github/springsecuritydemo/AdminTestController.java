package com.github.springsecuritydemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminTestController {

    @RequestMapping("/home")
    public String productInfo(){
        return " admin home page ";
    }
}
