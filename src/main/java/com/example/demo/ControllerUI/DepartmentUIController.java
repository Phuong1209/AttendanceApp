package com.example.demo.ControllerUI;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.service.Department.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

}
