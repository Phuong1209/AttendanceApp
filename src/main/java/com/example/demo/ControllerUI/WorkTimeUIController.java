package com.example.demo.ControllerUI;

import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.WorkTime;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    //Show Create form
    @GetMapping("/create")
    public String createWorkTimeForm(Model model){
        WorkTime workTime = new WorkTime();
        model.addAttribute("workTime", workTime);
        return "worktime/worktime-create";
    }

    //Create
    @PostMapping("/create")
    public String saveWorkTime(@ModelAttribute("workTime") WorkTime workTime){
        workTimeService.saveWorkTime(workTime);
        return "redirect:/worktimes";
    }

    //Show edit form
    @GetMapping("/{workTimeId}")
    public String editWorkTimeForm(@PathVariable("workTimeId") long workTimeId, Model model){
        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        model.addAttribute("workTimeDto", workTimeDto);
        return "worktime/worktime-edit";
    }

    //Edit
    @PostMapping("/{workTimeId}")
    public String updateWorkTime(@PathVariable("workTimeId") Long workTimeId,
                                   @ModelAttribute("workTimeDto") WorkTimeDTO workTimeDto){
        workTimeDto.setId(workTimeId);
        workTimeService.updateWorkTime(workTimeDto);
        return "redirect:/worktimes";
    }

    //Delete
    @GetMapping("/{workTimeId}/delete")
    public String deleteWorkTime(@PathVariable("workTimeId")long workTimeId){
        workTimeService.delete(workTimeId);
        return "redirect:/worktimes";
    }

}
