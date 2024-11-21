package com.example.demo.controller;

import com.example.demo.service.User.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationControllerUI {
    private UserService userService;

    public AuthenticationControllerUI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

}
