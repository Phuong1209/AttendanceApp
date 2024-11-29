package com.example.demo.uicontroller;

import com.example.demo.service.User.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthUIController {
    private UserService userService;

    public AuthUIController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}
