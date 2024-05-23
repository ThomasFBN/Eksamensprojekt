package com.example.eksamensprojekt.RepositoryTest;

import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Repository.SubProjectRepository;
import com.example.eksamensprojekt.Repository.TaskRepository;
import com.example.eksamensprojekt.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SubProjectRepositoryTest {

    @Autowired
    private SubProjectRepository subProjectRepository;
    @Autowired

    private TaskRepository taskRepository;
    @Autowired

    private UserRepository userRepository;

    @Test
    void createSubProject() throws SQLException {
        SubProject subProject = new SubProject("Test SubProject123", "I gang", 1);
        subProjectRepository.createSubProject(subProject);
        assertNotNull(subProject.getSubProjectId());
    }

    @Test
    void findTasksBySubProjectId() throws SQLException {
        SubProject subProject = new SubProject("Test SubProject1", "I gang", 1);
        subProjectRepository.createSubProject(subProject);

        taskRepository.createTask(new Task("Test Task1", subProject.getSubProjectId(), LocalDate.now(), LocalDate.now().plusDays(1), 10, "I gang"));

        List<Task> tasks = subProjectRepository.findTasksBySubProjectId(subProject.getSubProjectId());
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
    }

    @Test
    void findUsersBySubProjectId() throws SQLException {
        SubProject subProject = new SubProject("Test SubProject12", "I gang", 3);
        subProjectRepository.createSubProject(subProject);

        Task task = new Task("Test Task12", subProject.getSubProjectId(), LocalDate.now(), LocalDate.now().plusDays(1), 10, "I gang");
        taskRepository.createTask(task);
        User user = new User("testuser123", "password", "employee");
        userRepository.createUser(user);
        taskRepository.assignUserToTask(task.getTaskId(), user.getUser_ID());

        List<User> users = subProjectRepository.findUsersBySubProjectId(subProject.getSubProjectId());
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    void deleteSubProject() throws SQLException {
        SubProject subProject = new SubProject("Test SubProject11", "I gang", 1);
        subProjectRepository.createSubProject(subProject);
        assertNotNull(subProject.getSubProjectId());

        subProjectRepository.deleteSubProject(subProject.getSubProjectId());
        SubProject deletedSubProject = subProjectRepository.findSubProjectById(subProject.getSubProjectId());
        assertNull(deletedSubProject);
    }

    @Test
    void editSubProject() throws SQLException {
        SubProject subProject = new SubProject("Test SubProject22", "I gang", 1);
        subProjectRepository.createSubProject(subProject);
        assertNotNull(subProject.getSubProjectId());

        subProject.setSubProjectName("Updated SubProject");
        subProject.setStatus("Færdig");
        subProjectRepository.editSubProject(subProject, subProject.getSubProjectId());

        SubProject updatedSubProject = subProjectRepository.findSubProjectById(subProject.getSubProjectId());
        assertNotNull(updatedSubProject);
        assertEquals("Updated SubProject", updatedSubProject.getSubProjectName());
        assertEquals("Færdig", updatedSubProject.getStatus());
    }

    @Test
    void findSubProjectById() throws SQLException {
        SubProject subProject = new SubProject("Test SubProject33", "I gang", 1);
        subProjectRepository.createSubProject(subProject);

        SubProject foundSubProject = subProjectRepository.findSubProjectById(subProject.getSubProjectId());
        assertNotNull(foundSubProject);
        assertEquals(subProject.getSubProjectId(), foundSubProject.getSubProjectId());
    }
}
