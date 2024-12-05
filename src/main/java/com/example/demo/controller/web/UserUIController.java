package com.example.demo.controller.web;



import com.example.demo.dto.UserDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.service.User.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/userui")
public class UserUIController {
    @Autowired
    private IUserService userService;

    //Show User List
    @GetMapping({""})
    public String GetUserList(Model model) {
        Iterable<UserDTO> users =  userService.getAllUser();
        model.addAttribute("users", users);
        return "user/index";


    }

}
