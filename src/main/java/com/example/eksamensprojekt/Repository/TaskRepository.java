package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class TaskRepository {
    private Task task;
    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_username;
    @Value("${spring.datasource.password}")
    private String db_password;


    public void createTask(Task task) throws SQLException {
        String SQL = "INSERT INTO tasks (taskName, subproject_id, startDate, endDate, estTime, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
             PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, task.getTaskName());
            statement.setInt(2, task.getSubprojectId());
            statement.setDate(3, Date.valueOf(task.getStartDate()));
            statement.setDate(4, Date.valueOf(task.getEndDate()));
            statement.setInt(5, task.getEstTime());
            statement.setString(6, task.getStatus());
            statement.setInt(7, task.getUserId());
            statement.executeUpdate();
        }
    }


}
