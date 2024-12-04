package com.example.demo.ControllerUI;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.Department;
import com.example.demo.model.JobType;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/worktimes")
public class WorkTimeUIController {
    @Autowired
    private IWorkTimeService workTimeService;
    @Autowired
    private ITaskRepository taskRepository;

    //Show WorkTime List
    @GetMapping({"","/"})
    public String listWorkTime(Model model) {
        List<WorkTimeDTO> workTimes = workTimeService.getAllWorkTime();
        model.addAttribute("workTimes", workTimes);
        return "worktime/worktime-list";
    }

}
