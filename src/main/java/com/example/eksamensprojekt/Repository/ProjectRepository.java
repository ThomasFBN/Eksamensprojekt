package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
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

}
