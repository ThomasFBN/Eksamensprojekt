package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class TaskController {
    private TaskService taskService;
    public TaskController(TaskService taskService ) {
        this.taskService = taskService;
    }

    @GetMapping("/subProjectDetails/{subProjectId}/createTask")
    public String showCreateTaskForm(@PathVariable("subProjectId") int subProjectId, Model model, HttpSession session) {
        if ("pjManager".equals(((User) session.getAttribute("user")).getRole())) {
            model.addAttribute("subProjectId", subProjectId);
            model.addAttribute("task", new Task());
            return "createTask";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/subProjectDetails/{subProjectId}/createTask")
    public String createTask(@PathVariable("subProjectId") int subProjectId, @ModelAttribute Task task, HttpSession session) throws SQLException {
        if ("pjManager".equals(((User) session.getAttribute("user")).getRole())) {
            task.setSubprojectId(subProjectId);
            taskService.createTask(task);
            return "redirect:/subProjectDetails/" + subProjectId;
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/editTask/{taskId}")
    public String getEditTaskForm(@PathVariable("taskId") int taskId, Model model, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && ("pjManager".equals(user.getRole()) || "employee".equals(user.getRole()))) {
            Task task = taskService.findTaskByTaskId(taskId);
            model.addAttribute("task", task);
            model.addAttribute("taskId", taskId);
            return "editTask";
        } else {
            return "redirect:/";
        }
    }




    @PostMapping("/editTask/{taskId}")
    public String editTask(@ModelAttribute Task task, @PathVariable("taskId") int taskId, HttpSession session) throws SQLException {
        taskService.editTask(task, taskId);
        int subprojectId = taskService.findTaskByTaskId(taskId).getSubprojectId();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String role = user.getRole();
            if ("employee".equals(role)) {
                return "redirect:/employee/" + user.getUser_ID();
            } else if ("pjManager".equals(role)) {
                return "redirect:/subProjectDetails/" + subprojectId;
            }
        }

        return "redirect:/";
    }

    @PostMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable("id") int id, HttpSession session) throws SQLException {
        if ("pjManager".equals(((User) session.getAttribute("user")).getRole())) {
            int subProjectId = taskService.findTaskByTaskId(id).getSubprojectId();
            taskService.deleteTask(id);
            return "redirect:/subProjectDetails/" + subProjectId;
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/employee/{userId}")
    public String showTasks(@PathVariable("userId") int userId, Model model, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getUser_ID() == userId) {
            List<Task> allTasks = taskService.showAllTasks(userId);
            List<Task> completedTasks = taskService.showCompletedTasks(userId);

            model.addAttribute("tasks", allTasks);
            model.addAttribute("completedTasks", completedTasks);

            return "employee";
        }
        return "redirect:/";
    }


    @PostMapping("/employee/{userId}/complete/{taskId}")
    public String markTaskAsCompleted(@PathVariable("userId") int userId, @PathVariable("taskId") int taskId, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getUser_ID() == userId) {
            taskService.markTaskAsCompleted(taskId);
            return "redirect:/employee/" + userId;
        }
        return "redirect:/";
    }

    @PostMapping("/assignUserToTask")
    public String assignUserToTask(@RequestParam("taskId") int taskId, @RequestParam("userId") int userId, HttpSession session) throws SQLException {
        if ("pjManager".equals(((User) session.getAttribute("user")).getRole())) {
            taskService.assignUserToTask(taskId, userId);
            int subProjectId = taskService.findTaskByTaskId(taskId).getSubprojectId();
            return "redirect:/subProjectDetails/" + subProjectId;
        } else {
            return "redirect:/";
        }
    }
}





