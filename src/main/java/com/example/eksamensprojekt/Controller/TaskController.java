package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

@Controller
public class TaskController {
    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/subProjectDetails/{subProjectId}/createTask")
    public String showCreateTaskForm(@PathVariable("subProjectId") int subProjectId, Model model) {
        model.addAttribute("subProjectId", subProjectId);
        model.addAttribute("task", new Task());
        return "createTask";
    }

    @PostMapping("/subProjectDetails/{subProjectId}/createTask")
    public String createTask(@PathVariable("subProjectId") int subProjectId, @ModelAttribute Task task) throws SQLException {
        task.setSubprojectId(subProjectId);
        taskService.createTask(task);
        int taskId = task.getTaskId();
        return "redirect:/subProjectDetails/" + subProjectId;
    }

    @GetMapping("/editTask/{taskId}")
    public String getEditTaskForm(@PathVariable("taskId") int taskId, Model model) throws SQLException {
        Task task = taskService.findTaskByTaskId(taskId);
        model.addAttribute("task", task);
        model.addAttribute("taskId", taskId);

        return "editTask";
    }

    @PostMapping("/editTask/{taskId}")
    public String editTask(@ModelAttribute Task task, @PathVariable("taskId") int taskId) throws SQLException {
        taskService.editTask(task, taskId);
        int subprojectId = taskService.findTaskByTaskId(taskId).getSubprojectId();

        return "redirect:/subProjectDetails/" + subprojectId;
    }

    @PostMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") int id) throws SQLException {
        int subProjectid= taskService.findTaskByTaskId(id).getSubprojectId();
        Task task = taskService.findTaskByTaskId(id);
        taskService.deleteTask(id);
        return "redirect:/subProjectDetails/" + subProjectid;
    }





}
