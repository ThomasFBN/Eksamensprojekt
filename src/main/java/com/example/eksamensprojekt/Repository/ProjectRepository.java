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

    public List<Project> findProjectsByUserId(int userId) throws SQLException {
        List<Project> projects = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT p.project_id, " + "p.projectName, " + "p.status, " +
                "COUNT(DISTINCT sp.subproject_id) AS subproject_count, " +
                "COUNT(DISTINCT t.user_id) AS user_count " + "FROM projects p " +
                "LEFT JOIN subprojects sp ON p.project_id = sp.project_id " +
                "LEFT JOIN tasks t ON sp.subproject_id = t.subproject_id " + "WHERE p.user_id = ? " +
                "GROUP BY p.project_id, p.projectName, p.status";

        try (PreparedStatement projectsPS = connection.prepareStatement(SQL)) {
            projectsPS.setInt(1, userId);
            ResultSet projectsRS = projectsPS.executeQuery();
            while (projectsRS.next()) {
                Project project = new Project();
                project.setProject_id(projectsRS.getInt("project_id"));
                project.setProjectName(projectsRS.getString("projectName"));
                project.setStatus(projectsRS.getString("status"));
                project.setUserCount(projectsRS.getInt("user_count"));
                project.setSubProjectCount(projectsRS.getInt("subproject_count"));

                projects.add(project);
            }
        }
        return projects;
    }



}




