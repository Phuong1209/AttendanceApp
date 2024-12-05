package com.example.demo.controller.web;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/createuserui")
public class CreateUserUIController {
    @Autowired
    private IUserService userService;

    //Show User List

}
