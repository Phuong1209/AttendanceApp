package com.example.demo.ControllerUI;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.model.JobType;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.WorkTime;
import com.example.demo.repository.IJobTypeRepository;
import com.example.demo.repository.IProjectRepository;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.service.Task.ITaskService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskUIController {
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IJobTypeRepository jobTypeRepository;

    //Show Task List
    @GetMapping({"","/"})
    public String listTask(Model model) {
        List<TaskDTO> tasks = taskService.getAllTask();
        model.addAttribute("tasks", tasks);
        return "task/task-list";
    }

    //Show Create form
    @GetMapping("/create")
    public String createTaskForm(Model model){
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

    //Create
    @PostMapping("/create")
    public String saveTask(@ModelAttribute("task") Task task){
        taskService.saveTask(task);
        return "redirect:/tasks";
    }

    //Show edit form
    @GetMapping("/{taskId}")
    public String editTaskForm(@PathVariable("taskId") long taskId, Model model){
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

    //Edit
    @PostMapping("/{taskId}")
    public String updateTask(@PathVariable("taskId") Long taskId,
                                 @ModelAttribute("taskDto") TaskDTO taskDto){
        taskDto.setId(taskId);
        taskService.updateTask(taskDto);
        return "redirect:/tasks";
    }

    //Delete
    @GetMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable("taskId")long taskId){
        taskService.delete(taskId);
        return "redirect:/tasks";
    }


}
