package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.ProjectService;
import com.example.eksamensprojekt.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ProjectController {
    private ProjectService projectService;
    private UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;

    }

    @PostMapping("/projectManager")
    public String createProject(@ModelAttribute Project project, @RequestParam int userId) throws SQLException {
        projectService.createProject(project, userId);
        return "redirect:/projectManager";
    }


    @GetMapping("/showProjects/{userId}")
    public String showProjects(Model model, @PathVariable("userId") int userId, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getUser_ID() == userId) {
            List<Project> projectList = projectService.findProjectsByUserId(userId);
            model.addAttribute("projectList", projectList);
            return "showProjects";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/projectDetails/{projectId}")
    public String projectDetails(@PathVariable int projectId, Model model) throws SQLException {
        List<SubProject> subProjects = projectService.findSubProjectsByProjectId(projectId);
        List<User> users = projectService.findUsersByProjectId(projectId);

        model.addAttribute("subProjects", subProjects);
        model.addAttribute("users", users);

        return "projectDetails";
    }


}

