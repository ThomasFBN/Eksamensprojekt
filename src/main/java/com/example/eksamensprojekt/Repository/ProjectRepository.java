package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {
    private Project project;
    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_username;
    @Value("${spring.datasource.password}")
    private String db_password;


    public void createProject(Project project, int user_id) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        {
            String SQL = "INSERT INTO PROJECTS (projectName,user_id) VALUES (?,?)";

            try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, project.getProjectName());
                ps.setInt(2, user_id);


                int rowsAffected = ps.executeUpdate();
                ResultSet resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    long generatedId = resultSet.getLong(1);
                    project.setProject_id((int) generatedId);
                }

            }
        }
    }
    public List<Project> showProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT *, (SELECT COUNT(*) FROM tasks WHERE project_id = projects.project_id) " +
                "AS task_count, (SELECT COUNT(*) FROM users WHERE user_id = projects.user_id) " +
                "AS user_count FROM projects";
        try (PreparedStatement projectsPS = connection.prepareStatement(SQL)) {
            ResultSet projectsRS = projectsPS.executeQuery();
            while (projectsRS.next()) {
                Project project = new Project();
                project.setProject_id(projectsRS.getInt("project_id"));
                project.setProjectName(projectsRS.getString("projectName"));
                project.setStatus(projectsRS.getString("status"));

                project.setTaskCount(projectsRS.getInt("task_count"));
                project.setUserCount(projectsRS.getInt("user_count"));

                projects.add(project);
            }
        }

        return projects;
    }


}
