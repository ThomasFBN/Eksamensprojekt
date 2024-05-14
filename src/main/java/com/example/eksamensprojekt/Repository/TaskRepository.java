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

    public void deleteTask(int task_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url,db_username,db_password);
            String SQL = "DELETE FROM subtask WHERE task_id = ?";
            String SQL1 = "DELETE FROM task WHERE task_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            PreparedStatement pstmt1 = con.prepareStatement(SQL1);
            pstmt.setInt(1, task_id);
            pstmt.executeUpdate();
            pstmt1.setInt(1, task_id);
            pstmt1.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void editTask(Task task, int task_id, int project_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url,db_username,db_password);
            String SQL = "UPDATE task SET TaskName = ?, start_date = ?, end_date = ?, EstTime = ?, status =?, WHERE task_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, task.getTaskName());
                pstmt.setDate(2, java.sql.Date.valueOf(task.getStartDate()));
                pstmt.setDate(3, java.sql.Date.valueOf(task.getEndDate()));
                pstmt.setInt(4, task.getEstTime());
                pstmt.setString(5, task.getStatus());

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }



}
