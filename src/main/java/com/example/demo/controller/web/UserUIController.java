package com.example.demo.controller.web;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PositionDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IPositionRepository;
import com.example.demo.service.User.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.PasswordAuthentication;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/members")
public class UserUIController {
    @Autowired
    private IUserService userService;
    @Autowired
    IPositionRepository positionRepository;
    @Autowired
    IDepartmentRepository departmentRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    //Show User List
    @GetMapping({""})
    public String getUserList(Model model) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/index";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user/CreateUser";
    }

    @PostMapping("/create")
    public String saveUser(@ModelAttribute("user") User model) {
        userService.save(model);
        return "redirect:/members";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            return "redirect:/members?error=User not found";
        }
        //get list position
        List<Department> departments =departmentRepository.findAll();
        List<Position> positions = positionRepository.findAll();
        model.addAttribute("user", user.get());
        model.addAttribute("departments", departments);
        model.addAttribute("positions", positions);
        return "user/EditUser";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @Valid
                             @ModelAttribute("user") User updatedUser,
                             @RequestParam(value = "positionIds", required = false) Set<Long> positionIds,
                             @RequestParam(value = "departmentIds", required = false) Set<Long> departmentIds,
                             Model model, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "user/EditUser";
        }

        // Lấy thông tin người dùng cũ
        Optional<User> optionalUser = userService.findById(id);
        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "User not found.");
            return "user/EditUser";
        }

        User existingUser = optionalUser.get();

        // Cập nhật thông tin cơ bản
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setUserName(updatedUser.getUserName());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Cập nhật position
        if (positionIds != null) {
            List<Position> positions = positionRepository.findAllById(positionIds);
            Set<Position> positionSet = new LinkedHashSet<>(positions);
            existingUser.setPositions(positionSet);
        }

        // Cập nhật department
        if (departmentIds != null) {
            List<Department> departments = departmentRepository.findAllById(departmentIds);
            Set<Department> departmentSet = new LinkedHashSet<>(departments);
            existingUser.setDepartments(departmentSet);
        }

        // Lưu thông tin người dùng
        userService.save(existingUser);
        return "redirect:/members";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model){
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            userService.remove(id);
            // Nếu muốn hiển thị thông báo về người dùng đã xóa
            model.addAttribute("message", "User has been deleted successfully.");
        } else {
            model.addAttribute("error", "User not found.");
        }
        return "redirect:/members"; // Chuyển hướng về trang userui
    }

}