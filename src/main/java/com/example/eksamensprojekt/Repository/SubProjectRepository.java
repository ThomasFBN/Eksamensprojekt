package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
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
            ResultSet resultSet = ps.getGeneratedKeys();
            if (resultSet.next()) {
                long generatedId = resultSet.getLong(1);
                subProject.setSubProjectId((int) generatedId);
            }
        }
    }

    public List<Task> findTasksBySubProjectId(int subProjectId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT t.*, u.username " +
                "FROM tasks t " +
                "INNER JOIN users u ON t.user_id = u.user_id " +
                "WHERE t.subproject_id = ?";

        try (PreparedStatement tasksPS = connection.prepareStatement(SQL)) {
            tasksPS.setInt(1, subProjectId);
            ResultSet tasksRS = tasksPS.executeQuery();
            while (tasksRS.next()) {
                Task task = new Task();
                task.setTaskId(tasksRS.getInt("task_id"));
                task.setTaskName(tasksRS.getString("taskName"));
                task.setStartDate(tasksRS.getDate("startDate").toLocalDate());
                task.setEndDate(tasksRS.getDate("endDate").toLocalDate());
                task.setEstTime(tasksRS.getInt("estTime"));
                task.setStatus(tasksRS.getString("status"));
                task.setUserId(tasksRS.getInt("user_id"));
                task.setUsername(tasksRS.getString("username"));

                tasks.add(task);
            }
        }
        return tasks;
    }

    public List<User> findUsersBySubProjectId(int subProjectId) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT u.* FROM users u " +
                "INNER JOIN tasks t ON u.user_id = t.user_id " +
                "WHERE t.subproject_id = ?";

        try (PreparedStatement usersPS = connection.prepareStatement(SQL)) {
            usersPS.setInt(1, subProjectId);
            ResultSet usersRS = usersPS.executeQuery();
            while (usersRS.next()) {
                User user = new User();
                user.setUser_ID(usersRS.getInt("user_id"));
                user.setUsername(usersRS.getString("username"));
                users.add(user);
            }
        }
        return users;
    }



}
