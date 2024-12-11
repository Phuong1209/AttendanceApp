package com.example.demo.controller.web;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.*;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.repository.IProjectRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.security.SecurityUtil;
import com.example.demo.service.Task.TaskService;
import com.example.demo.service.User.UserService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

    //Show all workTime
    @GetMapping({"","/"})
    public String listAllWorkTime(Model model) {
        List<WorkTimeDTO> workTimes = workTimeService.getAllWorkTime();
        model.addAttribute("workTimes", workTimes);
        return "worktime/worktime-list-all";
    }

    //Show user's workTime
    @GetMapping("/user{userId}")
    public String listUserWorkTime(@PathVariable("userId") Long userId, Model model) {
        List<WorkTime> workTimes = userService.getWorkTimeByUser(userId);
        model.addAttribute("workTimes", workTimes);
        return "worktime/worktime-list-all";
    }

    //Show user's workTime
    /*@GetMapping("/user{userId}")
    public String listUserWorkTime(@PathVariable("userId") Long userId, Model model) {
        //add user to model
        User user = userService.findById(userId).orElse(null);
        model.addAttribute("user", user);

        // Get the current date
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();

        //add workTime to model
        Iterable<WorkTimeDTO> workTimes = workTimeService.getWorkTimeForUserAndMonth(userId, year, month);
        model.addAttribute("workTimes", workTimes);

        // Add attributes to the model for rendering
        model.addAttribute("userFullName", user.getFullName());
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("currentYear", year);
        model.addAttribute("currentMonth", month);
        model.addAttribute("workTimes", workTimes);

        // Add navigation buttons for previous and next months
        model.addAttribute("previousMonth", month == 1 ? 12 : month - 1);
        model.addAttribute("previousYear", month == 1 ? year - 1 : year);
        model.addAttribute("nextMonth", month == 12 ? 1 : month + 1);
        model.addAttribute("nextYear", month == 12 ? year + 1 : year);

        return "worktime/worktime-list-user";
    }*/

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
        //get logged in user
        String username = SecurityUtil.getSessionUser();
        User loggedInUser = userService.findByUserName(username);

        //set user
        workTime.setUser(loggedInUser);

        workTimeService.saveWorkTime(workTime);
        return "redirect:/worktimes/user" + workTime.getUser().getId();
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
        // After updating, redirect to the user's attendance screen
        return "redirect:/worktimes/users/" + workTimeDto.getUser().getId();
    }

    //Delete
    @GetMapping("/{workTimeId}/delete")
    public String deleteWorkTime(@PathVariable("workTimeId") long workTimeId) {
        WorkTimeDTO workTimeDto = workTimeService.findById(workTimeId);
        workTimeService.delete(workTimeId);
        // After deleting, redirect to the user's attendance screen
        return "redirect:/worktimes/users/" + workTimeDto.getUser().getId();
    }

    // Admin Path - Show List of Users
    @GetMapping("/admin")
    public String listUsers(Model model) {
        Iterable<User> users = userService.findAll();
        System.out.println("Users: " + users); // Debugging output
        model.addAttribute("users", users);
        return "worktime/user_worktime_list";
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
        boolean isTasklimitReached = tasks.size() >= 5;
        model.addAttribute("isTaskLimitReached", isTasklimitReached);

        return "worktime/worktime-tasks";
    }

    // Show Create Task Form
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
}
