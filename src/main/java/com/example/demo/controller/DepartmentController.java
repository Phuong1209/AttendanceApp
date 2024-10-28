package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/departments")

public class DepartmentController {
    @Autowired
    private DepartmentRepository repo;

    @GetMapping({"","/"})
    public String showDepartmentList(Model model){
        List<Department> departments = repo.findAll();
        model.addAttribute("departments", departments);
        return "department/index";
    }
}
