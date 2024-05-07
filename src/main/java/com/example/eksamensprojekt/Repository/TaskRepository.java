package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
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
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "INSERT INTO tasks (subproject_id, taskName, description, status, user_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, task.getSubProjectId());
            preparedStatement.setString(2, task.getTaskName());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getStatus());
            preparedStatement.setInt(5, task.getUserId());

            preparedStatement.executeUpdate();
        }
    }

}
