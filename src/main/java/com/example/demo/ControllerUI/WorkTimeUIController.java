package com.example.demo.ControllerUI;

import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import com.example.demo.service.User.IUserService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/worktimes")
public class WorkTimeUIController {

    @Autowired
    private IWorkTimeService workTimeService;

    @Autowired
    private IUserService userService;

    // Admin screen: List all users
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
        model.addAttribute("userName", user.getName()); // Set the user's name as the title
        model.addAttribute("currentYear", year);
        model.addAttribute("currentMonth", month);
        model.addAttribute("workTimes", workTimes);

        // Add navigation buttons for previous and next months
        model.addAttribute("previousMonth", month == 1 ? 12 : month - 1);
        model.addAttribute("previousYear", month == 1 ? year - 1 : year);
        model.addAttribute("nextMonth", month == 12 ? 1 : month + 1);
        model.addAttribute("nextYear", month == 12 ? year + 1 : year);

        return "worktime/worktime_list"; // Thymeleaf template for individual worktimes
    }
}

/*    // Admin screen: List all users
    @GetMapping("/admin")
    public String listUsers(Model model) {
        Iterable<User> users = userService.findAll(); // Fetch all users
        model.addAttribute("users", users);
        return "worktime/user_worktime_list"; // Thymeleaf template name
    }

    // Detailed WorkTime List for a User
    @GetMapping("/user/{id}")
    public String listWorkTime(@PathVariable Long id, Model model) {
        Iterable<WorkTime> workTimes = workTimeService.findAllByUserId(id); // Fetch worktimes by user ID
        model.addAttribute("workingTimes", workTimes);
        model.addAttribute("user", userService.findById(id).orElse(null)); // Add user information
        return "worktime/worktime_list"; // Thymeleaf template for individual worktimes
    }
}*/
