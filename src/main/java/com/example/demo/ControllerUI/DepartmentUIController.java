package com.example.demo.ControllerUI;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.model.User;
import com.example.demo.service.Department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/departments")
public class DepartmentUIController {
    @Autowired
    private IDepartmentService departmentService;

    //Show Department List
    @GetMapping({"","/"})
    public String listDepartment(Model model) {
        List<DepartmentDTO> departments = departmentService.getAllDepartment();
        model.addAttribute("departments", departments);
        return "department/department-list";
    }

    @GetMapping("/create")
    public String createDepartmentForm(Model model){
        Department department = new Department();
        model.addAttribute("department", department);
        return "department/department-create";
    }

    @PostMapping("/create")
    public String saveDepartment(@ModelAttribute("department") Department department){
        departmentService.saveDepartment(department);
        return "redirect:/departments";
    }

/*    //Create Department
    //1. Show Create form
    @GetMapping("/create")
    public String showCreatePage(Model model){
        DepartmentDto departmentDto = new DepartmentDto(); //declare dto
        model.addAttribute("departmentDto", departmentDto); //add dto to model

        //show list jobtype
        List<JobType> jobTypes = jobtypeRepository.findAll();
        model.addAttribute("jobTypes", jobTypes);//
        return "department/CreateDepartment";
    }

    //2. Create Department to DB
    @PostMapping("/create")
    public String createDepartment(
            @Valid @ModelAttribute DepartmentDto departmentDto,
            BindingResult result
    ) {

        // Check if the department already exists
        if (departmentRepository.existsByName(departmentDto.getName())) {
            result.rejectValue("name", "error.departmentDto", "The department already exists");
        }

        if(result.hasErrors()){
            return "department/CreateDepartment";
        }

        //create new department in db
        Department department = new Department();
        department.setName(departmentDto.getName());

        // Convert department IDs to Department entities
        Set<JobType> jobTypes = new HashSet<>();
        for (Long jobTypeId : departmentDto.getJobTypeIds()) {
            JobType jobType = jobtypeRepository.findById(jobTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid JobType ID: " + jobTypeId));
            jobTypes.add(jobType);
        }
        department.setJobTypes(jobTypes);

        departmentRepository.save(department);

        return "redirect:/department";

    }

    //Edit Department
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ){

        try{
            Department department = departmentRepository.findById((long) id).get();
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
            Department department = departmentRepository.findById((long) id).get();
            model.addAttribute("department", department);

            if (departmentRepository.existsByName(departmentDto.getName())) {
                result.rejectValue("name", "error.departmentDto", "The department already exists");
            }

            if(result.hasErrors()){
                return "department/EditDepartment";
            }

            department.setName(departmentDto.getName());

            departmentRepository.save(department);

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
            Department department = departmentRepository.findById((long) id).get();
            departmentRepository.delete(department);

        }
        catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }

        return "redirect:/department";

    }

    //Show Memberlist:
    @GetMapping("/{id}/users")
    public String showMemberlist(@PathVariable("id") Long departmentId, Model model) {
        Set<User> users = departmentRepository.findUsersByDepartmentId(departmentId);

        if (users == null) {
            model.addAttribute("errorMessage", "Department not found or has no users.");
            return "redirect:/department";
        }

        model.addAttribute("users", users);

        // Fetch the department name
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid department Id:" + departmentId));
        model.addAttribute("departmentName", department.getName());

        return "department/MemberList";
    }

    //Show JobTypelist:
    @GetMapping("/{id}/jobtypes")
    public String showJobTypelist(@PathVariable("id") Long departmentId, Model model) {
        Set<JobType> jobTypes = departmentRepository.findJobTypesByDepartmentId(departmentId);

        if (jobTypes == null) {
            model.addAttribute("errorMessage", "Department not found or has no users.");
            return "redirect:/department";
        }

        model.addAttribute("jobTypes", jobTypes);

        // Fetch the department name
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid department Id:" + departmentId));
        model.addAttribute("departmentName", department.getName());

        return "department/JobTypeList";
    }*/


}
