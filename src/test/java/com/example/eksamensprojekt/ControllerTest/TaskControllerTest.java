package com.example.eksamensprojekt.ControllerTest;

import com.example.eksamensprojekt.Controller.TaskController;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.TaskService;
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

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowCreateTaskForm() {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        String viewName = taskController.showCreateTaskForm(1, model, session);
        verify(model).addAttribute("subProjectId", 1);
        verify(model).addAttribute(eq("task"), any(Task.class));
        assertEquals("createTask", viewName);
    }

    @Test
    public void testCreateTask() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        Task task = new Task();

        String viewName = taskController.createTask(1, task, session);
        verify(taskService).createTask(task);
        assertEquals("redirect:/subProjectDetails/1", viewName);
    }

    @Test
    public void testGetEditTaskForm() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        Task task = new Task();
        when(taskService.findTaskByTaskId(1)).thenReturn(task);

        String viewName = taskController.getEditTaskForm(1, model, session);
        verify(model).addAttribute("task", task);
        verify(model).addAttribute("taskId", 1);
        assertEquals("editTask", viewName);
    }

    @Test
    public void testEditTask() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        Task task = new Task();
        task.setSubprojectId(1);

        when(taskService.findTaskByTaskId(1)).thenReturn(task);

        String viewName = taskController.editTask(task, 1, session);
        verify(taskService).editTask(task, 1);
        assertEquals("redirect:/subProjectDetails/1", viewName);
    }

    @Test
    public void testDeleteTask() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        Task task = new Task();
        task.setSubprojectId(1);
        when(taskService.findTaskByTaskId(1)).thenReturn(task);

        String viewName = taskController.deleteTask(1, session);
        verify(taskService).deleteTask(1);
        assertEquals("redirect:/subProjectDetails/1", viewName);
    }

    @Test
    public void testShowTasks() throws SQLException {
        User mockUser = new User();
        mockUser.setUser_ID(1);

        when(session.getAttribute("user")).thenReturn(mockUser);

        List<Task> allTasks = new ArrayList<>();
        List<Task> completedTasks = new ArrayList<>();
        when(taskService.showAllTasks(1)).thenReturn(allTasks);
        when(taskService.showCompletedTasks(1)).thenReturn(completedTasks);

        String viewName = taskController.showTasks(1, model, session);
        verify(model).addAttribute("tasks", allTasks);
        verify(model).addAttribute("completedTasks", completedTasks);
        assertEquals("employee", viewName);
    }

    @Test
    public void testMarkTaskAsCompleted() throws SQLException {
        User mockUser = new User();
        mockUser.setUser_ID(1);

        when(session.getAttribute("user")).thenReturn(mockUser);

        String viewName = taskController.markTaskAsCompleted(1, 1, session);
        verify(taskService).markTaskAsCompleted(1);
        assertEquals("redirect:/employee/1", viewName);
    }

    @Test
    public void testAssignUserToTask() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("pjManager");

        when(session.getAttribute("user")).thenReturn(mockUser);

        Task task = new Task();
        task.setSubprojectId(1);
        when(taskService.findTaskByTaskId(1)).thenReturn(task);

        String viewName = taskController.assignUserToTask(1, 1, session);
        verify(taskService).assignUserToTask(1, 1);
        assertEquals("redirect:/subProjectDetails/1", viewName);
    }
}
