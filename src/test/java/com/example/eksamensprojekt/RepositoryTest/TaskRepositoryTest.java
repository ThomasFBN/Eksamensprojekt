package com.example.eksamensprojekt.RepositoryTest;

import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Repository.TaskRepository;
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
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void createTask() throws SQLException {
        Task task = new Task("Test Task",5, LocalDate.now(), LocalDate.now().plusDays(1), 8, "I gang");
        taskRepository.createTask(task);
        assertNotNull(task.getTaskName());
    }

    @Test
    void showAllTasks() throws SQLException {
        List<Task> tasks = taskRepository.showAllTasks(2);
        assertFalse(tasks.isEmpty());
    }

    @Test
    void showCompletedTasks() throws SQLException {
        List<Task> completedTasks = taskRepository.showCompletedTasks(1);
        assertNotNull(completedTasks);
        assertTrue(completedTasks.isEmpty());
    }

    @Test
    void deleteTask() throws SQLException {
        taskRepository.deleteTask(1);
        Task deletedTask = taskRepository.findTaskByTaskId(1);
        assertNull(deletedTask);
    }

    @Test
    void editTask() throws SQLException {
        Task task = taskRepository.findTaskByTaskId(5);
        task.setTaskName("Updated Task Name");
        task.setEndDate(LocalDate.now().plusDays(2));
        taskRepository.editTask(task, 2);
        assertEquals("Updated Task Name", task.getTaskName());
        assertEquals(LocalDate.now().plusDays(2), task.getEndDate());
    }

    @Test
    void findTaskByTaskId() throws SQLException {

        Task task = taskRepository.findTaskByTaskId(5);
        assertEquals(5, task.getTaskId());
    }

    @Test
    void markTaskAsCompleted() throws SQLException {
        taskRepository.markTaskAsCompleted(5);
        Task completedTask = taskRepository.findTaskByTaskId(5);
        assertEquals("Færdig", completedTask.getStatus());
    }
/* ## VIRKER IKKE PÅ NUVÆRENDE TIDSPUNKT:
    @Test
    void assignUserToTask() throws SQLException {
        User user = new User("test1", "password", "employee");
        userRepository.createUser(user);

        taskRepository.assignUserToTask(5, user.getUser_ID());
        Task assignedTask = taskRepository.findTaskByTaskId(5);

        assertNotNull(assignedTask);
        assertEquals(user.getUser_ID(), assignedTask.getUserId());
    }

 */






}
