package com.example.demo.ControllerUI;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.*;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.repository.IProjectRepository;
import com.example.demo.service.Task.TaskService;
import com.example.demo.service.User.UserService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/worktimes")
public class WorkTimeUIController {
    @Autowired
    private IWorkTimeService workTimeService;
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IJobTypeRepository jobTypeRepository;
    @Autowired
    private IProjectRepository projectRepository;

    // Show WorkTime List
    @GetMapping({"", "/"})
    public String listWorkTime(Model model) {
        List<WorkTimeDTO> workTimes = workTimeService.getAllWorkTime();
        model.addAttribute("workTimes", workTimes);
        return "worktime/worktime-list";
    }

    // Show Create Form
    @GetMapping("/create")
    public String createWorkTimeForm(Model model) {
        WorkTime workTime = new WorkTime();
        model.addAttribute("workTime", workTime);
        return "worktime/worktime-create";
    }

    // Create
    @PostMapping("/create")
    public String saveWorkTime(@ModelAttribute("workTime") WorkTime workTime) {
        workTimeService.saveWorkTime(workTime);
        // After creating, redirect to the user's attendance screen
        return "redirect:/worktimes/user/" + workTime.getUser().getId();
    }

    // Show Edit Form
    @GetMapping("/{workTimeId}")
    public String editWorkTimeForm(@PathVariable("workTimeId") long workTimeId, Model model) {
        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        model.addAttribute("workTimeDto", workTimeDto);
        return "worktime/worktime-edit";
    }

    // Edit
    @PostMapping("/{workTimeId}")
    public String updateWorkTime(@PathVariable("workTimeId") Long workTimeId,
                                 @ModelAttribute("workTimeDto") WorkTimeDTO workTimeDto) {
        workTimeDto.setId(workTimeId);
        workTimeService.updateWorkTime(workTimeDto);
        // After updating, redirect to the user's attendance screen
        return "redirect:/worktimes/user/" + workTimeDto.getUser().getId();
    }

    // Delete
    @GetMapping("/{workTimeId}/delete")
    public String deleteWorkTime(@PathVariable("workTimeId") long workTimeId) {
        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        workTimeService.delete(workTimeId);
        // After deleting, redirect to the user's attendance screen
        return "redirect:/worktimes/user/" + workTimeDto.getUser().getId();
    }

    // Admin Path - Show List of Users
    @GetMapping("/admin")
    public String listUsers(Model model) {
        Iterable<User> users = userService.findAll();
        System.out.println("Users: " + users); // Debugging output
        model.addAttribute("users", users);
        return "worktime/user_worktime_list";
    }

    // Show Task List
    @GetMapping("/user/user{id}/tasks")
    public String showTaskList(@PathVariable("workTimeId") Long workTimeId, Model model) {
        // Fetch the workTime info
        WorkTimeDTO workTime = workTimeService.findById(workTimeId);
        model.addAttribute("date", workTime.getDate());
        model.addAttribute("fullName", workTime.getUser().getFullName());

        // Add workTimeId to model
        model.addAttribute("workTimeId", workTimeId);

        // Add task to model
        Set<Task> tasks = workTimeService.findTasksByWorkTime(workTimeId);
        if (tasks == null) {
            model.addAttribute("errorMessage", "タスクを登録してください。");
            return "redirect:/worktimes";
        }
        model.addAttribute("tasks", tasks);

        // Check task number
        boolean isTasklimitReached = tasks.size() >= 20;
        model.addAttribute("isTaskLimitReached", isTasklimitReached);

        return "worktime/worktime-tasks";
    }

    // Show Create Task Form
    @GetMapping("{workTimeId}/tasks/create")
    public String createTaskForm(@PathVariable("workTimeId") Long workTimeId, Model model) {
        model.addAttribute("workTimeId", workTimeId);

        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        model.addAttribute("workTimeDto", workTimeDto);

        Task task = new Task();
        model.addAttribute("task", task);

        // Show list of projects
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);

        // Show list of job types
        List<JobType> jobTypes = jobTypeRepository.findAll();
        model.addAttribute("jobTypes", jobTypes);

        return "task/task-create";
    }

    // Create Task
    @PostMapping("{workTimeId}/tasks/create")
    public String saveTask(@PathVariable("workTimeId") Long workTimeId,
                           @ModelAttribute("task") Task task) {
        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        WorkTime workTime = WorkTime.builder()
                .id(workTimeDto.getId())
                .date(workTimeDto.getDate())
                .build();

        task.setWorkTime(workTime);
        taskService.saveTask(task);
        return "redirect:/worktimes/" + workTimeId + "/tasks";
    }

    // Show Edit Task Form
    @GetMapping("{workTimeId}/tasks/{taskId}")
    public String editTaskForm(@PathVariable("workTimeId") Long workTimeId,
                               @PathVariable("taskId") long taskId,
                               Model model) {
        TaskDTO taskDto = taskService.findById(taskId);
        model.addAttribute("task", taskDto);

        // Show list of projects
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);

        // Show list of job types
        List<JobType> jobTypes = jobTypeRepository.findAll();
        model.addAttribute("jobTypes", jobTypes);

        return "task/task-edit";
    }

    // Edit Task
    @PostMapping("{workTimeId}/tasks/{taskId}")
    public String updateTask(@PathVariable("taskId") Long taskId,
                             @PathVariable("workTimeId") Long workTimeId,
                             @ModelAttribute("taskDto") TaskDTO taskDto) {
        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        taskDto.setWorkTime(workTimeDto);
        taskDto.setId(taskId);
        taskService.updateTask(taskDto);
        return "redirect:/worktimes/" + workTimeId + "/tasks";
    }

    // Delete Task
    @GetMapping("{workTimeId}/tasks/{taskId}/delete")
    public String deleteTask(@PathVariable("taskId") long taskId,
                             @PathVariable("workTimeId") Long workTimeId) {
        taskService.delete(taskId);
        return "redirect:/worktimes/" + workTimeId + "/tasks";
    }

    // User Attendance Sheet (Details)
    // Uncommented for use
    @GetMapping("/user/{userId}")
    public String listWorkTime(@PathVariable Long userId, Model model) {
        LocalDate today = LocalDate.now(); // Get the current date
        int year = today.getYear();
        int month = today.getMonthValue();

        // Fetch the user by ID
        User user = userService.findById(userId).orElse(null);
        if (user == null) {
            return "error/404"; // Handle case where user is not found
        }

        // Fetch attendance data for the user's current month
        Iterable<WorkTimeDTO> workTimes = workTimeService.getWorkTimeForUserAndMonth(userId, year, month);

        // Add attributes to the model for rendering
        model.addAttribute("userFullName", user.getFullName()); // Add the user's full name here
        model.addAttribute("userName", user.getUserName()); // Retain username for other uses
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
