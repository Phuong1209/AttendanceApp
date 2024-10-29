package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.dto.DepartmentDto;
import com.example.demo.repository.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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

        // Check if the department already exists
        if (repo.existsByName(departmentDto.getName())) {
            result.rejectValue("name", "error.departmentDto", "The department already exists");
        }

        if(result.hasErrors()){
            return "department/CreateDepartment";
        }

        //create new department in db
        Department department = new Department();
        department.setName(departmentDto.getName());

        repo.save(department);

        return "redirect:/department";

    }

    //Edit Department
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
            ){

        try{
            Department department = repo.findById((long) id).get();
            model.addAttribute("department", department);

            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setName(department.getName());

            model.addAttribute("departmentDto", departmentDto);

        }
        catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/department";
        }

        return "department/EditDepartment";

    }

    @PostMapping("/edit")
    public String editDepartment(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute DepartmentDto departmentDto,
            BindingResult result
            ){

        try{
            Department department = repo.findById((long) id).get();
            model.addAttribute("department", department);

            if (repo.existsByName(departmentDto.getName())) {
                result.rejectValue("name", "error.departmentDto", "The department already exists");
            }

            if(result.hasErrors()){
                return "department/EditDepartment";
            }

            department.setName(departmentDto.getName());

            repo.save(department);

        }
        catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/department";

    }

    //Delete Department
    @GetMapping("/delete")
    public String deleteDepartment(
            @RequestParam int id
    ){

        try{
            Department department = repo.findById((long) id).get();

            repo.delete(department);

        }
        catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/department";

    }

}
