package com.example.eksamensprojekt.Controller;

import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            User user = userService.checkLogin(username, password);
            int userId = user.getUser_ID();
            if (user != null && user.getRole().equals("admin")) {
                session.setAttribute("user", user);
                return "redirect:/admin";
            } else if (user != null && user.getRole().equals("employee")) {
                session.setAttribute("user", user);
                return "redirect:/employee/" + userId;
            } else if (user != null && user.getRole().equals("pjManager")) {
                session.setAttribute("user", user);
                return "redirect:/projectManager";
            } else {
                model.addAttribute("error", "invalid");
                return "home";
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/admin")
    public String admin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().equals("admin")) {
            return "admin";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/employee")
    public String employee(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().equals("employee")) {
            return "employee";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/projectManager")
    public String projectManager(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().equals("pjManager")) {
            model.addAttribute("user", user);
            return "projectManager";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/createUser")
    public String createUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null && "admin".equals(user.getRole())) {
            User defaultUser = new User();
            model.addAttribute("user", defaultUser);
            return "createUser";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/createUser")
    public String postCreateUser(@ModelAttribute User user, HttpSession session) throws SQLException {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser != null && "admin".equals(currentUser.getRole())) {
            userService.createUser(user);
            return "redirect:/admin";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/showUsers")
    public String showUsers(Model model, HttpSession session) throws SQLException {
        User user = (User) session.getAttribute("user");
        if (user != null && "admin".equals(user.getRole())) {
            List<User> userList = userService.showAllUsers();
            model.addAttribute("userList", userList);
            return "showUsers";
        } else {
            return "redirect:/";
        }
    }
}



