package com.example.eksamensprojekt.ControllerTest;


import com.example.eksamensprojekt.Controller.ProjectController;
import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.ProjectService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowCreateProjectForm() {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        String viewName = projectController.showCreateProjectForm(model, session);
        verify(model).addAttribute("userId", mockUser.getUser_ID());
        verify(model).addAttribute(eq("project"), any(Project.class));
        assertEquals("createProject", viewName);
    }

    @Test
    public void testPostCreateProject() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        Project project = new Project();
        int userId = mockUser.getUser_ID();

        String viewName = projectController.postCreateProject(project, userId, session);
        verify(projectService).createProject(project, userId);
        assertEquals("redirect:/projectManager", viewName);
    }

    @Test
    public void testShowProjects() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        List<Project> projectList = new ArrayList<>();
        when(projectService.findProjectsByUserId(mockUser.getUser_ID())).thenReturn(projectList);

        String viewName = projectController.showProjects(model, mockUser.getUser_ID(), session);
        verify(model).addAttribute("projectList", projectList);
        assertEquals("showProjects", viewName);
    }

    @Test
    public void testProjectDetails() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        List<SubProject> subProjects = new ArrayList<>();
        when(projectService.findSubProjectsByProjectId(1)).thenReturn(subProjects);

        String viewName = projectController.projectDetails(1, model, session);
        verify(model).addAttribute("subProjects", subProjects);
        assertEquals("projectDetails", viewName);
    }

    @Test
    public void testDeleteProject() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        int userId = mockUser.getUser_ID();

        String viewName = projectController.deleteProject(1, session);
        verify(projectService).deleteProject(1);
        assertEquals("redirect:/showProjects/" + userId, viewName);
    }

    @Test
    public void testGetEditProjectForm() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        Project project = new Project();
        when(projectService.findProjectById(1)).thenReturn(project);

        String viewName = projectController.getEditProjectForm(1, model, session);
        verify(model).addAttribute("project", project);
        verify(model).addAttribute("userId", project.getUser_id());
        assertEquals("editProject", viewName);
    }

    @Test
    public void testEditProject() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        Project project = new Project();
        project.setUser_id(mockUser.getUser_ID());

        String viewName = projectController.editProject(project, 1, session);
        verify(projectService).editProject(project, 1);
        assertEquals("redirect:/showProjects/" + mockUser.getUser_ID(), viewName);
    }
}

