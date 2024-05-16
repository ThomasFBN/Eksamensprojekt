package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.SubProjectService;
import com.example.eksamensprojekt.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
public class SubProjectController {
    private SubProjectService subProjectService;

    public SubProjectController(SubProjectService subProjectService) {
        this.subProjectService = subProjectService;
    }

    @GetMapping("/projectDetails/{projectId}/createSubProject")
    public String showCreateSubProjectForm(@PathVariable("projectId") int projectId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            model.addAttribute("projectId", projectId);
            model.addAttribute("subProject", new SubProject());
            return "createSubProject";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/projectDetails/{projectId}/createSubProject")
    public String createSubProject(@PathVariable("projectId") int projectId, @ModelAttribute SubProject subProject, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            subProject.setProjectId(projectId);
            subProjectService.createSubProject(subProject);
            return "redirect:/projectDetails/" + projectId;
        } else {
            return "redirect:/";
        }
    }


    @GetMapping("/subProjectDetails/{subProjectId}")
    public String subProjectDetails(@PathVariable int subProjectId, Model model, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            List<Task> tasks = subProjectService.findTasksBySubProjectId(subProjectId);
            List<User> users = subProjectService.findUsersBySubProjectId(subProjectId);
            model.addAttribute("tasks", tasks);
            model.addAttribute("users", users);
            return "subProjectDetails";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/editSubProject/{subProjectId}")
    public String getEditSubProjectForm(@PathVariable("subProjectId") int subProjectId, Model model, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            SubProject subProject = subProjectService.findSubProjectById(subProjectId);
            model.addAttribute("subProject", subProject);
            model.addAttribute("subProjectId", subProjectId);
            return "editSubProject";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/editSubProject/{subProjectId}")
    public String editSubProject(@ModelAttribute SubProject subProject, @PathVariable("subProjectId") int subProjectId, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            subProjectService.editSubProject(subProject, subProjectId);
            int projectId = subProjectService.findSubProjectById(subProjectId).getProjectId();
            return "redirect:/projectDetails/" + projectId;
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/deleteSubProject/{id}")
    public String deleteSubProject(@PathVariable("id") int id, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "pjManager".equals(user.getRole())) {
            int projectId = subProjectService.findSubProjectById(id).getProjectId();
            SubProject subProject = subProjectService.findSubProjectById(id);
            subProjectService.deleteSubProject(id);
            return "redirect:/projectDetails/" + projectId;
        } else {
            return "redirect:/";
        }
    }
}
