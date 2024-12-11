package com.example.demo.security;

import com.example.demo.service.User.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/login",""})
    public String loginPage(){
        return "/login";
    }

}