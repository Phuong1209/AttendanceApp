//Tuan Anh
package com.example.demo.controllerUI;


import com.example.demo.dto.ProjectDTO;
import com.example.demo.model.Project;
import com.example.demo.service.Project.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectUIController {
    @Autowired
    private IProjectService projectService;
//    @Autowired
//    private IJobTypeRepository jobTypeRepository;

    //Show Department List
    @GetMapping({"","/"})
    public String listProject(Model model) {
        List<ProjectDTO> projects = projectService.getAllProject();
        model.addAttribute("projects", projects);
        return "project/project-list";
    }

    @GetMapping("/create")
    public String createProjectForm(Model model){
        Project project = new Project();
        model.addAttribute("project", project);


        return "project/project-create";
    }

    @PostMapping("/create")
    public String saveProject(@ModelAttribute("project") Project project,
                              BindingResult result, Model model){
        if(result.hasErrors()) {
            model.addAttribute("project", project);
            return "project-create";
        }
//        List<JobType> selectedJobTypes = jobTypeRepository.findAllById(jobTypes);
//        project.setJobTypes(new HashSet<>(selectedJobTypes));

        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @GetMapping("/{projectId}/edit")
    public String editProjectForm(@PathVariable("projectId") Long projectId, Model model) {
        ProjectDTO projectDTO = projectService.findProjectById(projectId);
        model.addAttribute("project", projectDTO);
        return "project/project-edit";
    }
    @PostMapping("/{projectId}/edit")
    public String updateProject(@PathVariable("projectId") Long projectId,
                                @ModelAttribute("project") ProjectDTO project,
                                BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("project", project);
            return "project-edit";
        }
        project.setId(projectId);
        projectService.updateProject(project);
        return "redirect:/projects";
    }
//    @GetMapping("/{projectId}/delete")
//    public String deleteProject(@PathVariable("projectId")Long projectId) {
//        projectService.deleteByProjectId(projectId);
//        return "redirect:/projects";
//    }
    @GetMapping("/{projectId}/delete")
    public String deleteProject(@PathVariable("projectId") Long projectId, RedirectAttributes redirectAttributes) {
        boolean isDeletable = projectService.canDeleteProject(projectId);

        if (!isDeletable) {
            redirectAttributes.addFlashAttribute("error", "プロジェクトを削除できません。作業と繋がられます。");
            return "redirect:/projects";
        }

        projectService.deleteByProjectId(projectId);
        redirectAttributes.addFlashAttribute("success", "プロジェクトが削除されました。");
        return "redirect:/projects";
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
