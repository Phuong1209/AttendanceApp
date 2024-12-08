package com.example.demo.ControllerUI;

import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.service.User.UserService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/worktimes")
public class WorkTimeUIController {
    @Autowired
    private IWorkTimeService workTimeService;
    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private UserService userService;

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

    @GetMapping("/admin")
    public String listUsers(Model model) {
        Iterable<User> users = userService.findAll(); // Fetch all users
        model.addAttribute("users", users);
        return "worktime/user_worktime_list"; // Thymeleaf template name
    }

    // User Attendance Sheet (Details)
    @GetMapping("/user/{id}")
    public String listWorkTime(@PathVariable Long id, Model model) {
        LocalDate today = LocalDate.now(); // Get the current date
        int year = today.getYear();
        int month = today.getMonthValue();

        // Fetch the user by ID
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            return "error/404"; // Handle case where user is not found
        }

        // Fetch attendance data for the user's current month
        Iterable<WorkTimeDTO> workTimes = workTimeService.getWorkTimeForUserAndMonth(id, year, month);

        // Add attributes to the model for rendering
        model.addAttribute("userName", user.getUserName()); // Set the user's name as the title
        model.addAttribute("currentYear", year);
        model.addAttribute("currentMonth", month);
        model.addAttribute("workTimes", workTimes);

        // Add navigation buttons for previous and next months
        model.addAttribute("previousMonth", month == 1 ? 12 : month - 1);
        model.addAttribute("previousYear", month == 1 ? year - 1 : year);
        model.addAttribute("nextMonth", month == 12 ? 1 : month + 1);
        model.addAttribute("nextYear", month == 12 ? year + 1 : year);

        return "worktime/worktime_list";
    }

}
