package com.example.demo.ControllerUI;

import com.example.demo.dto.TaskDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.repository.ITaskRepository;
import com.example.demo.service.Task.ITaskService;
import com.example.demo.service.WorkTime.IWorkTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskUIController {
    @Autowired
    private ITaskService taskService;

    //Show Task List
    @GetMapping({"","/"})
    public String listTask(Model model) {
        List<TaskDTO> tasks = taskService.getAllTask();
        model.addAttribute("tasks", tasks);
        return "task/task-list";
    }

}
