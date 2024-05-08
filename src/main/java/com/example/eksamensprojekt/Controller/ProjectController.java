package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.ProjectService;
import com.example.eksamensprojekt.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String createProject(@ModelAttribute Project project, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");

        projectService.createProject(project, user.getUser_ID());
        return "projectManager";

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
    public String projectDetails(Model model, @PathVariable("projectId")int projectId) throws SQLException {

        return "projectDetails";
    }

}

