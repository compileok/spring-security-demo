package com.github.springsecuritydemo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductTestController {

    @RequestMapping("/info")
    public String productInfo(){
        return " some product info ";
    }

}
