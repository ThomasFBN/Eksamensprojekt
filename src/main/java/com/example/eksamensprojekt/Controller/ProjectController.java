package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.Task;
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
    @GetMapping("/createProject")
    public String showCreateProjectForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("userId", user.getUser_ID());
            model.addAttribute("project", new Project());
            return "createProject";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/createProject")
    public String postCreateProject(@ModelAttribute Project project, @RequestParam int userId) throws SQLException {
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


    @PostMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable("id") int id, HttpSession session) throws SQLException {
        int userId = ((User) session.getAttribute("user")).getUser_ID();
        Project project = projectService.findProjectById(id);
        projectService.deleteProject(id);
        return "redirect:/showProjects/" + userId;
    }

    @GetMapping("/editProject/{projectId}")
    public String getEditProjectForm(@PathVariable("projectId") int projectId, Model model) throws SQLException {
        Project project = projectService.findProjectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("userId", project.getUser_id());
        return "editProject";
    }





    @PostMapping("/editProject/{projectId}")
    public String editProject(@ModelAttribute Project project, @PathVariable("projectId") int projectId, HttpSession session) throws SQLException {
        int userId = ((User) session.getAttribute("user")).getUser_ID();
        project.setUser_id(userId);
        projectService.editProject(project, projectId);
        return "redirect:/showProjects/" + userId;
    }







}

