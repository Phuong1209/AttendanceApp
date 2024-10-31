package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.User;
import com.example.demo.model.dto.DepartmentDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    //Show list User
    //1. Create User Repo
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    //2. Create method showUserList
    @GetMapping({"","/"})
    public String showUserList(Model model){
        List<User> users = userRepository.findAll(); //find all user in repo
        model.addAttribute("users", users); //add user to model
        return "user/index";
    }

    //Create User
    //Dropdown list
/*
    static List<String> departmentList = null;

    static {
        departmentList = new ArrayList<>();
        departmentList.add("Developer");
        departmentList.add("Manager");
        departmentList.add("Consultant");
        departmentList.add("Tester");
    }
*/

    //Show Create form
    @GetMapping("/create")
    public String showCreatePage1(Model model){
        UserDto userDto = new UserDto(); //declare dto
        model.addAttribute("userDto", userDto); //add dto to model

        List<Department> departments = departmentRepository.findAll();
        model.addAttribute("departments", departments);//
        return "user/CreateUser";
    }

    //2. Create User to DB
    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute UserDto userDto,
            BindingResult result
    ) {

        if(userRepository.existsByUsername(userDto.getUsername())){
            result.rejectValue("username", "error.userDto", "The username already exists");
        }

        if(result.hasErrors()){
            return "user/CreateUser";
        }

        //create new user in db
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setFullname(userDto.getFullname());
        user.setPassword(userDto.getPassword());

        userRepository.save(user);

        return "redirect:/user";

    }

    //Edit User
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ){

        try{
            User user = userRepository.findById((long) id).get();
            model.addAttribute("user", user);

            UserDto userDto = new UserDto();
            userDto.setUsername(user.getUsername());
            userDto.setFullname(user.getFullname());
            userDto.setPassword(user.getPassword());

            model.addAttribute("userDto", userDto);

        }
        catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/user";
        }

        return "user/EditUser";

    }

    @PostMapping("/edit")
    public String editDepartment(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute UserDto userDto,
            BindingResult result
    ){

        try{
            User user = userRepository.findById((long) id).get();
            model.addAttribute("user", user);

            if (userRepository.existsByUsername(userDto.getUsername())) {
                result.rejectValue("username", "error.userDto", "The username already exists");
            }

            if(result.hasErrors()){
                return "user/EditUser";
            }

            user.setUsername(userDto.getUsername());
            user.setFullname(userDto.getFullname());
            user.setPassword(userDto.getPassword());

            userRepository.save(user);

        }
        catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/user";

    }

    //Delete User
    @GetMapping("/delete")
    public String deleteUser(
            @RequestParam int id
    ){

        try{
            User user = userRepository.findById((long) id).get();

            userRepository.delete(user);

        }
        catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/user";

    }


}
