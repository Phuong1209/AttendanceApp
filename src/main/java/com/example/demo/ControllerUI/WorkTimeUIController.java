package com.example.demo.ControllerUI;

import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.WorkTime;
import com.example.demo.service.JobType.IJobTypeService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/worktimes")
public class WorkTimeUIController {
    @Autowired
    private IWorkTimeService workTimeService;
    private Object workTime;

    //Show work time List
    @GetMapping({"", "/"})
    public String listWorkTime(Model model) {
        Iterable<WorkTime> workTimes = workTimeService.findAll();
        model.addAttribute("workTime", workTimes);
        return "worktime/worktime_list";
    }
    }