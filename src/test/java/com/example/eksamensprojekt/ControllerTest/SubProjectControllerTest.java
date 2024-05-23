package com.example.eksamensprojekt.ControllerTest;

import com.example.eksamensprojekt.Controller.SubProjectController;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.SubProjectService;
import com.example.eksamensprojekt.Service.UserService;
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

public class SubProjectControllerTest {

    @Mock
    private SubProjectService subProjectService;

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private SubProjectController subProjectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowCreateSubProjectForm() {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        String viewName = subProjectController.showCreateSubProjectForm(1, model, session);
        verify(model).addAttribute("projectId", 1);
        verify(model).addAttribute(eq("subProject"), any(SubProject.class));
        assertEquals("createSubProject", viewName);
    }

    @Test
    public void testCreateSubProject() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        SubProject subProject = new SubProject();
        subProject.setProjectId(1);

        String viewName = subProjectController.createSubProject(1, subProject, session);
        verify(subProjectService).createSubProject(subProject);
        assertEquals("redirect:/projectDetails/1", viewName);
    }

    @Test
    public void testSubProjectDetails() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        List<Task> tasks = new ArrayList<>();
        List<User> allUsers = new ArrayList<>();
        List<User> subProjectUsers = new ArrayList<>();

        when(subProjectService.findTasksBySubProjectId(1)).thenReturn(tasks);
        when(userService.showAllUsers()).thenReturn(allUsers);
        when(subProjectService.findUsersBySubProjectId(1)).thenReturn(subProjectUsers);

        String viewName = subProjectController.subProjectDetails(1, model, session);
        verify(model).addAttribute("tasks", tasks);
        verify(model).addAttribute("users", allUsers);
        verify(model).addAttribute("subProjectUsers", subProjectUsers);
        verify(model).addAttribute("subProjectId", 1);
        assertEquals("subProjectDetails", viewName);
    }

    @Test
    public void testGetEditSubProjectForm() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        SubProject subProject = new SubProject();
        when(subProjectService.findSubProjectById(1)).thenReturn(subProject);

        String viewName = subProjectController.getEditSubProjectForm(1, model, session);
        verify(model).addAttribute("subProject", subProject);
        verify(model).addAttribute("subProjectId", 1);
        assertEquals("editSubProject", viewName);
    }

    @Test
    public void testEditSubProject() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        SubProject subProject = new SubProject();
        SubProject existingSubProject = new SubProject();
        existingSubProject.setProjectId(1);

        when(subProjectService.findSubProjectById(1)).thenReturn(existingSubProject);

        String viewName = subProjectController.editSubProject(subProject, 1, session);
        verify(subProjectService).editSubProject(subProject, 1);
        assertEquals("redirect:/projectDetails/1", viewName);
    }

    @Test
    public void testDeleteSubProject() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        SubProject subProject = new SubProject();
        subProject.setProjectId(1);

        when(subProjectService.findSubProjectById(1)).thenReturn(subProject);

        String viewName = subProjectController.deleteSubProject(1, session);
        verify(subProjectService).deleteSubProject(1);
        assertEquals("redirect:/projectDetails/1", viewName);
    }
}
