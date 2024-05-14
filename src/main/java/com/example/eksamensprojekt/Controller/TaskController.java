package com.example.eksamensprojekt.Controller;

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
    public String createSubProject(@PathVariable("subProjectId") int subProjectId, @ModelAttribute Task task) throws SQLException {
        task.setSubprojectId(subProjectId);
        taskService.createTask(task);
        int taskId = task.getTaskId();
        return "redirect:/subProjectDetails/" + subProjectId;
    }

    @GetMapping("/editTask/{id}")
    public String getEditTaskForm(@PathVariable("id") int id, Model model) throws SQLException {
        Task task = taskService.findTaskById(id);
        model.addAttribute("wish", wish);
        return "editWish";
    }


    @PostMapping("/editTask/{id}")
    public String editTask(@ModelAttribute Task task, @PathVariable("id") int id) throws SQLException {
        TaskService.editTask(wish, id);
        int wishlistId = taskService.findTaskIdByTaskId(id);
        return "redirect:/showWish/" + wishlistId;
    }
}
