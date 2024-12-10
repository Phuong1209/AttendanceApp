package com.example.demo.controller.web;

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

    //Show task list:
    @GetMapping("/{workTimeId}/tasks")
    public String showTaskList(@PathVariable("workTimeId") Long workTimeId, Model model) {
        // Fetch the workTime info
        WorkTimeDTO workTime = workTimeService.findById(workTimeId);
        model.addAttribute("date", workTime.getDate());
        model.addAttribute("fullName", workTime.getUser().getFullName());

        //add workTimeId to model
        model.addAttribute("workTimeId", workTimeId);

        //add task to model
        Set<Task> tasks = workTimeService.findTasksByWorkTime(workTimeId);
        if (tasks == null) {
            model.addAttribute("errorMessage", "タスクを登録してください。");
            return "redirect:/worktimes";
        }
        model.addAttribute("tasks", tasks);

        //check task number
        boolean isTasklimitReached = tasks.size() >= 20;
        model.addAttribute("isTaskLimitReached", isTasklimitReached);

        return "worktime/worktime-tasks";
    }

    //Create task
    //Show Create form
    @GetMapping("{workTimeId}/tasks/create")
    public String createTaskForm(@PathVariable("workTimeId") Long workTimeId,
                                 Model model){
        model.addAttribute("workTimeId", workTimeId);

        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        model.addAttribute("workTimeDto", workTimeDto);

        Task task = new Task();
        model.addAttribute("task", task);

        //show list project
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);

        //show list jobtype
        List<JobType> jobTypes = jobTypeRepository.findAll();
        model.addAttribute("jobTypes", jobTypes);

        return "task/task-create";
    }

    //Create task
    @PostMapping("{workTimeId}/tasks/create")
    public String saveTask(@PathVariable("workTimeId") Long workTimeId,
                           @ModelAttribute("task") Task task){
        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        WorkTime workTime = WorkTime.builder()
                        .id(workTimeDto.getId())
                        .date(workTimeDto.getDate())
                        .build();

        task.setWorkTime(workTime);
        taskService.saveTask(task);
        return "redirect:/worktimes/" + workTimeId + "/tasks";
    }

    //Show edit task form
    @GetMapping("{workTimeId}/tasks/{taskId}")
    public String editTaskForm(@PathVariable("workTimeId") Long workTimeId,
                               @PathVariable("taskId") long taskId,
                               Model model){
        TaskDTO taskDto = taskService.findById(taskId);
        model.addAttribute("task", taskDto);

        //show list project
        List<Project> projects = projectRepository.findAll();
        model.addAttribute("projects", projects);

        //show list jobtype
        List<JobType> jobTypes = jobTypeRepository.findAll();
        model.addAttribute("jobTypes", jobTypes);

        return "task/task-edit";
    }

    //Edit task
    @PostMapping("{workTimeId}/tasks/{taskId}")
    public String updateTask(@PathVariable("taskId") Long taskId,
                             @PathVariable("workTimeId") Long workTimeId,
                             @ModelAttribute("taskDto") TaskDTO taskDto){
        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        taskDto.setWorkTime(workTimeDto);
        taskDto.setId(taskId);
        taskService.updateTask(taskDto);
        return "redirect:/worktimes/" + workTimeId + "/tasks";
    }

    //Delete task
    @GetMapping("{workTimeId}/tasks/{taskId}/delete")
    public String deleteTask(@PathVariable("taskId")long taskId,
                             @PathVariable("workTimeId") Long workTimeId){
        taskService.delete(taskId);
        return "redirect:/worktimes/" + workTimeId + "/tasks";
    }

    // User Attendance Sheet (Details)
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
