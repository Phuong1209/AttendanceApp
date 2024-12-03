package com.example.demo.controller.web;

import com.example.demo.model.Department;
import com.example.demo.model.User;
import com.example.demo.service.Department.IDepartmentService;
import com.example.demo.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/userui")
public class UserWeb
    @Autowired
    private UserService userService;

    //Show User List
    @GetMapping("")
    public String getUserUI(Model model) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/index";
    }
}
