package com.example.demo.controller.web;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.service.Department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/departments")
public class DepartmentUIController {
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IJobTypeRepository jobTypeRepository;

    //Show Department List
    @GetMapping({"","/"})
    public String listDepartment(Model model) {
        List<DepartmentDTO> departments = departmentService.getAllDepartment();
        model.addAttribute("departments", departments);
        return "department/department-list";
    }

    //Show Create form
    @GetMapping("/create")
    public String createDepartmentForm(Model model){
        Department department = new Department();
        model.addAttribute("department", department);

        //show list jobtype
        List<JobType> jobTypes = jobTypeRepository.findAll();
        model.addAttribute("jobTypes", jobTypes);

        return "department/department-create";
    }

    //Create
    @PostMapping("/create")
    public String saveDepartment(@ModelAttribute("department") Department department,
                                 @RequestParam("jobTypes") List<Long> jobTypes){

        List<JobType> selectedJobTypes = jobTypeRepository.findAllById(jobTypes);
        department.setJobTypes(new HashSet<>(selectedJobTypes));

        departmentService.saveDepartment(department);
        return "redirect:/departments";
    }

    //Show edit form
    @GetMapping("/{departmentId}")
    public String editDepartmentForm(@PathVariable("departmentId") long departmentId, Model model){
        DepartmentDTO departmentDto = departmentService.findById(departmentId);
        model.addAttribute("department", departmentDto);

        //show list jobtype
        List<JobType> jobTypes = jobTypeRepository.findAll();
        model.addAttribute("jobTypes", jobTypes);

        System.out.println("Job Types: " + jobTypes);
        System.out.println("Department DTO: " + departmentDto);

        return "department/department-edit";
    }

    //Edit
    @PostMapping("/{departmentId}")
    public String updateDepartment(@PathVariable("departmentId") Long departmentId,
                                   @ModelAttribute("departmentDto") DepartmentDTO departmentDto){
        departmentDto.setId(departmentId);
        departmentService.updateDepartment(departmentDto);
        return "redirect:/departments";
    }

    //Delete
    @GetMapping("/{departmentId}/delete")
    public String deleteDepartment(@PathVariable("departmentId")long departmentId){
        departmentService.delete(departmentId);
        return "redirect:/departments";
    }

    //Show JobTypelist:
    @GetMapping("/{id}/jobtypes")
    public String showJobTypeList(@PathVariable("id") Long departmentId, Model model) {
        Set<JobType> jobTypes = departmentService.findJobTypesByDepartment(departmentId);

        if (jobTypes == null) {
            model.addAttribute("errorMessage", "Department has no users.");
            return "redirect:/department";
        }

        model.addAttribute("jobTypes", jobTypes);

        // Fetch the department name
        DepartmentDTO department = departmentService.findById(departmentId);
        model.addAttribute("departmentName", department.getName());

        return "department/department-jobtypes";
    }

    //Show Member list:
    @GetMapping("/{id}/members")
    public String showUserList(@PathVariable("id") Long departmentId, Model model) {
        Set<User> users = departmentService.findUsersByDepartment(departmentId);

        if (users == null) {
            model.addAttribute("errorMessage", "Department has no users.");
            return "redirect:/department";
        }

        model.addAttribute("users", users);

        // Fetch the department name
        DepartmentDTO department = departmentService.findById(departmentId);
        model.addAttribute("departmentName", department.getName());

        return "department/department-members";
    }
}
