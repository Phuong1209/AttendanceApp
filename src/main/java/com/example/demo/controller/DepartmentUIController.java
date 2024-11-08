package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.service.Department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/departmentui")
public class DepartmentUIController {
    @Autowired
    private IDepartmentService departmentService;

    //Show Department List
    @GetMapping({"","/"})
    public String showDepartmentList(Model model) {
        List<Department> departments = (List<Department>) departmentService.findAll();
        model.addAttribute("departments", departments);
        return "department/index";
    }

}
