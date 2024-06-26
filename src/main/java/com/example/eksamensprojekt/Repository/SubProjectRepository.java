package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.Task;
import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubProjectRepository {
    private SubProject subProject;
    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_username;
    @Value("${spring.datasource.password}")
    private String db_password;

    public void createSubProject(SubProject subProject) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "INSERT INTO subprojects (subprojectName, status, project_id) VALUES (?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, subProject.getSubProjectName());
            ps.setString(2, subProject.getStatus());
            ps.setInt(3, subProject.getProjectId());

            int rowsAffected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long generatedId = rs.getLong(1);
                subProject.setSubProjectId((int) generatedId);
            }
        }
    }

    public List<Task> findTasksBySubProjectId(int subProjectId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT t.*, u.username\n" +
                "FROM tasks t\n" +
                "LEFT JOIN users u ON t.user_id = u.user_id\n" +
                "WHERE t.subproject_id = ?\n";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, subProjectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                task.setTaskId(rs.getInt("task_id"));
                task.setTaskName(rs.getString("taskName"));
                task.setStartDate(rs.getDate("startDate").toLocalDate());
                task.setEndDate(rs.getDate("endDate").toLocalDate());
                task.setEstTime(rs.getInt("estTime"));
                task.setStatus(rs.getString("status"));
                task.setUserId(rs.getInt("user_id"));
                task.setUsername(rs.getString("username"));

                tasks.add(task);
            }
        }
        return tasks;
    }

    public List<User> findUsersBySubProjectId(int subProjectId) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT DISTINCT u.* FROM users u " +
                "INNER JOIN tasks t ON u.user_id = t.user_id " +
                "WHERE t.subproject_id = ?";


        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, subProjectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUser_ID(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                users.add(user);
            }
        }
        return users;
    }

    public void deleteSubProject(int subProject_id) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, db_username, db_password);
            String SQL = "DELETE FROM subProjects WHERE subProject_id = ?";
            PreparedStatement pstmt = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, subProject_id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void editSubProject(SubProject subProject, int subProjectId) {
        try {
            Connection con = ConnectionManager.getConnection(db_url, db_username, db_password);
            String SQL = "UPDATE subprojects SET subprojectName = ?, status = ? WHERE subProject_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(SQL)) {
                pstmt.setString(1, subProject.getSubProjectName());
                pstmt.setString(2, subProject.getStatus());
                pstmt.setInt(3, subProjectId);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public SubProject findSubProjectById(int subProjectId) throws SQLException {
        SubProject subProject = null;
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT * FROM subprojects WHERE subProject_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, subProjectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                subProject = new SubProject();
                subProject.setSubProjectId(rs.getInt("subProject_id"));
                subProject.setSubProjectName(rs.getString("subprojectName"));
                subProject.setStatus(rs.getString("status"));
                subProject.setProjectId(rs.getInt("project_id"));
            }
        }
        return subProject;
    }

}
