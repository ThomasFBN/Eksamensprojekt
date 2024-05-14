package com.example.eksamensprojekt.Service;

import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(Task task) throws SQLException {
        taskRepository.createTask(task);
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
}