package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.dto.DepartmentDto;
import com.example.demo.repository.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/department")

public class DepartmentController {

    //Show list Department
    //1. Create Department Repo
    @Autowired
    private DepartmentRepository repo;
    //2. Create method to show Department List
    @GetMapping({"","/"})
    public String showDepartmentList(Model model){
        List<Department> departments = repo.findAll(); //add Department list from DB to "departments"
        model.addAttribute("departments", departments); //add all department listed to model
        return "department/index"; //show department from model to user view
    }

    //Create Department
    //1. Show Create form
    @GetMapping("/create")
    public String showCreatePage(Model model){
        DepartmentDto departmentDto = new DepartmentDto(); //declare dto
        model.addAttribute("departmentDto", departmentDto); //add dto to model
        return "department/CreateDepartment";
    }

    //2. Create Department to DB
    @PostMapping("/create")
    public String createDepartment(
            @Valid @ModelAttribute DepartmentDto departmentDto,
            BindingResult result
            ) {

        if(result.hasErrors()){
            return "department/CreateDepartment";
        }

        //create new department in db
        Department department = new Department();
        department.setName(departmentDto.getName());

        repo.save(department);

        return "redirect:/department";

    }




}
