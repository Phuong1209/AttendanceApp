package com.example.demo.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/loginui")
public class LoginUIController {
    @GetMapping("")
    public String login()
    {
        return "login";
    }

}