package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.Model.User;
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
            String SQL = "INSERT INTO PROJECTS (projectName,user_id, status) VALUES (?,?,?)";

            try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, project.getProjectName());
                ps.setInt(2, user_id);
                ps.setString(3,project.getStatus());


                int rowsAffected = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    long generatedId = rs.getLong(1);
                    project.setProject_id((int) generatedId);
                }

            }
        }
    }

    /*public List<Project> findProjectsByUserId(int userId) throws SQLException {
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
    } */

    public List<Project> findProjectsByUserId(int userId) throws SQLException {
        List<Project> projects = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT project_id, projectName, status FROM projects WHERE user_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project project = new Project();
                project.setProject_id(rs.getInt("project_id"));
                project.setProjectName(rs.getString("projectName"));
                project.setStatus(rs.getString("status"));

                projects.add(project);
            }
        }
        return projects;
    }

    public List<SubProject> findSubProjectsByProjectId(int projectId) throws SQLException {
        List<SubProject> subProjects = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT subProject_id, subprojectName, status FROM subProjects WHERE project_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SubProject subProject = new SubProject();
                subProject.setSubProjectId(rs.getInt("subProject_id"));
                subProject.setSubProjectName(rs.getString("subprojectName"));
                subProject.setStatus(rs.getString("status"));

                subProjects.add(subProject);
            }
        }
        return subProjects;


    }

    public List<User> findUsersByProjectId(int projectId) throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT u.user_id, u.username " +
                "FROM users u " +
                "INNER JOIN projects p ON u.user_id = p.user_id " +
                "WHERE p.project_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, projectId);
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

    public void editProject(Project project, int projectId) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "UPDATE Projects SET projectName=?, status=? WHERE project_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {
            pstmt.setString(1, project.getProjectName());
            pstmt.setString(2, project.getStatus());
            pstmt.setInt(3, projectId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Project findProjectById(int projectId) throws SQLException {
        Project project = null;
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);

        String SQL = "SELECT * FROM projects WHERE project_id=?";

        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                project = new Project();
                project.setProject_id(rs.getInt("project_id"));
            }
        return project;
    }

    }


    public void deleteProject(int project_id) {
        try {
            Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
            connection.setAutoCommit(false);

            String SQL1 = "DELETE FROM project WHERE project_id = ?";
            PreparedStatement deleteProjectStatement = connection.prepareStatement(SQL1);
            deleteProjectStatement.setInt(1, project_id);
            deleteProjectStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}




