package com.example.eksamensprojekt.ControllerTest;

import com.example.eksamensprojekt.Controller.UserController;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHome() {
        String viewName = userController.home();
        assertEquals("home", viewName);
    }

    @Test
    public void testLogin() {
        String viewName = userController.login();
        assertEquals("login", viewName);
    }

    @Test
    public void testLoginPost_Success() throws SQLException {
        User mockUser = new User();
        mockUser.setUser_ID(1);
        mockUser.setRole("admin");

        when(userService.checkLogin("admin", "password")).thenReturn(mockUser);

        String viewName = userController.loginPost("admin", "password", session, model);
        verify(session).setAttribute("user", mockUser);
        assertEquals("redirect:/admin", viewName);
    }

    @Test
    public void testLoginPost_InvalidUser() throws SQLException {
        when(userService.checkLogin("invalid", "user")).thenReturn(null);

        String viewName = userController.loginPost("invalid", "user", session, model);
        verify(model).addAttribute("error", "invalid");
        assertEquals("home", viewName);
    }

    @Test
    public void testAdmin_AccessGranted() {
        User mockUser = new User();
        mockUser.setRole("admin");

        when(session.getAttribute("user")).thenReturn(mockUser);

        String viewName = userController.admin(session);
        assertEquals("admin", viewName);
    }

    @Test
    public void testAdmin_AccessDenied() {
        when(session.getAttribute("user")).thenReturn(null);

        String viewName = userController.admin(session);
        assertEquals("redirect:/", viewName);
    }

    @Test
    public void testCreateUser_AccessGranted() {
        User mockUser = new User();
        mockUser.setRole("admin");

        when(session.getAttribute("user")).thenReturn(mockUser);

        String viewName = userController.createUser(model, session);
        verify(model).addAttribute(eq("user"), any(User.class));
        assertEquals("createUser", viewName);
    }

    @Test
    public void testCreateUser_AccessDenied() {
        when(session.getAttribute("user")).thenReturn(null);

        String viewName = userController.createUser(model, session);
        assertEquals("redirect:/", viewName);
    }

    @Test
    public void testShowUsers_AccessGranted() throws SQLException {
        User mockUser = new User();
        mockUser.setRole("admin");

        when(session.getAttribute("user")).thenReturn(mockUser);

        String viewName = userController.showUsers(model, session);
        assertEquals("showUsers", viewName);
    }

    @Test
    public void testShowUsers_AccessDenied() throws SQLException {
        when(session.getAttribute("user")).thenReturn(null);

        String viewName = userController.showUsers(model, session);
        assertEquals("redirect:/", viewName);
    }
}
