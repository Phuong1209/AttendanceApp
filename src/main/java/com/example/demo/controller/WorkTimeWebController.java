package com.example.demo.controller;

import com.example.demo.model.WorkingTime;
import com.example.demo.service.WorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/worktime")
public class WorkTimeWebController {
    @Autowired
    private WorkTimeService workTimeService;

    @GetMapping("/worktime")
    public String getAllWorkTimeRecords(Model model) {
        List<WorkingTime> workingTimes = workTimeService.getAllWorkTimes();
        model.addAttribute("workingTimes", workingTimes);
        return "worktime_list";
    }

    // Display a form to create a new work time record
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("workingTime", new WorkingTime());
        return "worktime_create";
    }

    // Save a new work time record
    @PostMapping("/save")
    public String saveWorkTimeRecord(@ModelAttribute("workingTime") WorkingTime workingTime) {
        workTimeService.save(workingTime);
        return "redirect:/worktime";
    }

    // Display a form to update an existing work time record
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        WorkingTime workingTime = workTimeService.getById(id);
        model.addAttribute("workingTime", workingTime);
        return "worktime_edit";
    }

    // Update an existing work time record
    @PostMapping("/update/{id}")
    public String updateWorkTimeRecord(@PathVariable Long id, @ModelAttribute("workingTime") WorkingTime workingTime) {
        workingTime.setId(id);
        workTimeService.save(workingTime);
        return "redirect:/worktime";
    }

    // Delete a work time record
    @GetMapping("/delete/{id}")
    public String deleteWorkTimeRecord(@PathVariable Long id) {
        workTimeService.delete(id);
        return "redirect:/worktime";
    }

}