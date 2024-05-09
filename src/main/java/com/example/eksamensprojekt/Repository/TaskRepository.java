package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

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

        String SQL = "INSERT INTO tasks (taskName, subproject_id, startDate, endDate, estTime, status, user_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, task.getTaskName());
            ps.setInt(2, task.getSubprojectId());
            ps.setDate(3, java.sql.Date.valueOf(task.getStartDate()));
            ps.setDate(4, java.sql.Date.valueOf(task.getEndDate()));
            ps.setInt(5, task.getEstTime());
            ps.setString(6, task.getStatus());
            ps.setInt(7, task.getUserId());

            int rowsAffected = ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                long generatedId = resultSet.getLong(1);
                task.setTaskId((int) generatedId);
            }
        }
    }





}
