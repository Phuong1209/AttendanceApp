package com.example.demo.controller;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller

public class DepartmentController {
    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public String listDepartments(Model model){
        List<DepartmentDto> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        return "departments-list";
    }
}
