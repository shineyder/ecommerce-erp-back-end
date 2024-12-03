package com.shineyder.ecommerce_erp_back_end.controller;

import com.shineyder.ecommerce_erp_back_end.model.Users;
import com.shineyder.ecommerce_erp_back_end.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @Autowired
    private UsersService service;

    @PostMapping("/register")
    public Users register(@RequestBody Users users){
        return service.register(users);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users users){
        return service.verify(users);
    }
}
