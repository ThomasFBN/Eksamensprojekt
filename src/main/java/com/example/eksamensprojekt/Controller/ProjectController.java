package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.ProjectService;
import com.example.eksamensprojekt.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;

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

        projectService.createProject(project,user.getUser_ID());
        return "projectManager";

    }


}
