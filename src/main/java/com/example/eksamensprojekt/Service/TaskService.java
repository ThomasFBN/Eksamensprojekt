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

}