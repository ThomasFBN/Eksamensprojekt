package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        String SQL = "INSERT INTO tasks (taskName, subproject_id, startDate, endDate, estTime, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, task.getTaskName());
            ps.setInt(2, task.getSubprojectId());
            ps.setDate(3, java.sql.Date.valueOf(task.getStartDate()));
            ps.setDate(4, java.sql.Date.valueOf(task.getEndDate()));
            ps.setInt(5, task.getEstTime());
            ps.setString(6, task.getStatus());

            int rowsAffected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long generatedId = rs.getLong(1);
                task.setTaskId((int) generatedId);
            }
        }
    }

    public List<Task> showAllTasks(int userId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM TASKS WHERE USER_ID = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getInt("task_id"));
                task.setTaskName(rs.getString("taskName"));
                task.setSubprojectId(rs.getInt("subproject_id"));
                task.setStartDate(rs.getDate("startDate").toLocalDate());
                task.setEndDate(rs.getDate("endDate").toLocalDate());
                task.setEstTime(rs.getInt("estTime"));
                task.setStatus(rs.getString("status"));
                tasks.add(task);
            }
        }
        return tasks;
    }

    public void deleteTask(int task_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, db_username, db_password);
            String SQL = "DELETE FROM tasks WHERE task_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            pstmt.setInt(1, task_id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void editTask(Task task, int taskId) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, db_username, db_password);
            String SQL = "UPDATE tasks SET TaskName = ?, startDate = ?, endDate = ?, EstTime = ?, status = ? WHERE task_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, task.getTaskName());
                pstmt.setDate(2, java.sql.Date.valueOf(task.getStartDate()));
                pstmt.setDate(3, java.sql.Date.valueOf(task.getEndDate()));
                pstmt.setInt(4, task.getEstTime());
                pstmt.setString(5, task.getStatus());
                pstmt.setInt(6, taskId);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    public Task findTaskByTaskId(int taskId) throws SQLException {
        Task task = null;
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT * FROM tasks WHERE task_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, taskId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                task = new Task();
                task.setTaskId(rs.getInt("task_id"));
                task.setTaskName(rs.getString("taskName"));
                task.setSubprojectId(rs.getInt("subproject_id"));
                task.setStartDate(rs.getDate("startDate").toLocalDate());
                task.setEndDate(rs.getDate("endDate").toLocalDate());
                task.setEstTime(rs.getInt("estTime"));
                task.setStatus(rs.getString("status"));

            }
            return task;

        }


    }
    public void markTaskAsCompleted(int taskId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "UPDATE TASKS SET STATUS = 'Completed' WHERE TASK_ID = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, taskId);
            ps.executeUpdate();
        }
    }


}

