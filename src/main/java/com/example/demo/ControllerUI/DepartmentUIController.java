package com.example.demo.ControllerUI;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.JobTypeDTO;
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
import java.util.stream.Collectors;

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

    //Show JobTypelist:
    @GetMapping("/{id}/jobtypes")
    public String showJobTypelist(@PathVariable("id") Long departmentId, Model model) {
        Set<JobType> jobTypes = departmentService.findJobTypesByDepartmentId(departmentId);

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


/*//Edit Department
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
