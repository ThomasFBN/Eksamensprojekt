package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.SubProjectService;
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
    public String showCreateSubProjectForm(@PathVariable("projectId") int projectId, Model model) {
        model.addAttribute("projectId", projectId);
        model.addAttribute("subProject", new SubProject());
        return "createSubProject";
    }

    @PostMapping("/projectDetails/{projectId}/createSubProject")
    public String createSubProject(@PathVariable("projectId") int projectId, @ModelAttribute SubProject subProject) throws SQLException {
        subProject.setProjectId(projectId);
        subProjectService.createSubProject(subProject);
        return "redirect:/projectDetails/" + projectId;
    }


    @GetMapping("/subProjectDetails/{subProjectId}")
    public String subProjectDetails(@PathVariable int subProjectId, Model model) throws SQLException {
        List<Task> tasks = subProjectService.findTasksBySubProjectId(subProjectId);
        List<User> users = subProjectService.findUsersBySubProjectId(subProjectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("users", users);
        return "subProjectDetails";
    }


}
