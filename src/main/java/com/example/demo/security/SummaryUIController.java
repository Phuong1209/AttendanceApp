package com.example.demo.ControllerUI;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.JobTypeDTO;
import com.example.demo.dto.ProjectDTO;
import com.example.demo.dto.Summary.*;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.service.Department.IDepartmentService;
import com.example.demo.service.JobType.IJobTypeService;
import com.example.demo.service.Project.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/summary")
public class SummaryUIController {
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IJobTypeService jobTypeService;
    @Autowired
    private IProjectService projectService;
    @GetMapping({"","/"})
    public String listDepartment(Model model) {


        List<DepJobSummaryDTO> summaryByDepartment = departmentService.getDepJobSummary();
        model.addAttribute("summaryByDepartments", summaryByDepartment);

        List<ProjJobSummaryDTO> summaryByProject = projectService.getProjJobSummary();
        model.addAttribute("summaryByProjects", summaryByProject);

        List<DepProjSummaryDTO> summaryByDepartment3 = departmentService.getDepProjSummary();
        model.addAttribute("summaryByDepartment3s", summaryByDepartment3);

        return "summary/summary-lists";
    }

}
