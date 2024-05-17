package com.example.eksamensprojekt.Service;

import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(Task task) throws SQLException {
        taskRepository.createTask(task);
    }

    public List<Task> showAllTasks(int userId) throws SQLException {
        return taskRepository.showAllTasks(userId);
    }
    public List<Task> showCompletedTasks(int userId) throws SQLException {
        return taskRepository.showCompletedTasks(userId);
    }

    public void editTask(Task task, int task_id) {
        taskRepository.editTask(task, task_id);
    }

    public void deleteTask(int task_id) {
        taskRepository.deleteTask(task_id);
    }

    public Task findTaskByTaskId(int taskId) throws SQLException {
        return taskRepository.findTaskByTaskId(taskId);

    }

    public void markTaskAsCompleted(int taskId) throws SQLException {
        taskRepository.markTaskAsCompleted(taskId);
    }
    public void assignUserToTask(int taskId, int userId) throws SQLException {
        taskRepository.assignUserToTask(taskId, userId);
    }



    }