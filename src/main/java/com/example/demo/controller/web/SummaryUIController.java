package com.example.demo.controller.web;

import com.example.demo.dto.Summary.*;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.service.Department.DepartmentService;
import com.example.demo.service.Department.IDepartmentService;
import com.example.demo.service.JobType.IJobTypeService;
import com.example.demo.service.Project.IProjectService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @GetMapping({"/summaryProjectByDepartment"})
    public void generateCSV(HttpServletResponse response) throws IOException {
        List<DepartmentSummaryDTO3> summaries = departmentService.getSummaryByDepartment3();
        response.setContentType("CSVapplication/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        departmentService.exportDepProSummaryToCSV(response,summaries);
    }

}
