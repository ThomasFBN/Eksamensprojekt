package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/createProject")
    public String showCreateProjectForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            model.addAttribute("userId", user.getUser_ID());
            model.addAttribute("project", new Project());
            return "createProject";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/createProject")
    public String postCreateProject(@ModelAttribute Project project, @RequestParam int userId, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            projectService.createProject(project, userId);
            return "redirect:/projectManager";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/showProjects/{userId}")
    public String showProjects(Model model, @PathVariable("userId") int userId, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getUser_ID() == userId && "pjManager".equals(user.getRole())) {
            List<Project> projectList = projectService.findProjectsByUserId(userId);
            model.addAttribute("projectList", projectList);
            return "showProjects";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/projectDetails/{projectId}")
    public String projectDetails(@PathVariable int projectId, Model model, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            List<SubProject> subProjects = projectService.findSubProjectsByProjectId(projectId);

            model.addAttribute("subProjects", subProjects);

            return "projectDetails";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable("id") int id, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            int userId = user.getUser_ID();
            projectService.deleteProject(id);
            return "redirect:/showProjects/" + userId;
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/editProject/{projectId}")
    public String getEditProjectForm(@PathVariable("projectId") int projectId, Model model, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            Project project = projectService.findProjectById(projectId);
            model.addAttribute("project", project);
            model.addAttribute("userId", project.getUser_id());
            return "editProject";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/editProject/{projectId}")
    public String editProject(@ModelAttribute Project project, @PathVariable("projectId") int projectId, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            int userId = user.getUser_ID();
            project.setUser_id(userId);
            projectService.editProject(project, projectId);
            return "redirect:/showProjects/" + userId;
        } else {
            return "redirect:/";
        }
    }
}
