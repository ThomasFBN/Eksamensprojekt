package com.example.eksamensprojekt.RepositoryTest;

import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void findUser() throws SQLException {
        User user = new User("test1", "password", "employee");
        userRepository.createUser(user);

        User foundUser = userRepository.findUser("test1");
        assertNotNull(foundUser);
        assertEquals("test1", foundUser.getUsername());
    }

    @Test
    void checkLogin_Valid() throws SQLException {
        String username = "testuser";
        String password = "password";
        User user = new User(username, password, "admin");
        userRepository.createUser(user);

        User loggedInUser = userRepository.checkLogin(username, password);
        assertNotNull(loggedInUser);
        assertEquals(username, loggedInUser.getUsername());
    }

    @Test
    void checkLogin_Invalid() throws SQLException {
        String username = "testuser3";
        String password = "password";
        String wrongPassword = "wrongpassword";
        User user = new User(username, password, "employee");
        userRepository.createUser(user);

        User loggedInUser = userRepository.checkLogin(username, wrongPassword);
        assertNull(loggedInUser);
    }

    @Test
    void showAllUsers() throws SQLException {
        userRepository.createUser(new User("testuser1", "password", "admin"));
        userRepository.createUser(new User("testuser2", "password", "employee"));

        List<User> users = userRepository.showAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    void createUser() throws SQLException {
        User user = new User("createdUserTest", "password", "admin");
        userRepository.createUser(user);
        assertNotNull(user.getUsername());
    }

}
