package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        String SQL = "INSERT INTO subprojects (project_id, subprojectName, status) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, subProject.getProjectId());
            preparedStatement.setString(2, subProject.getSubProjectName());
            preparedStatement.setString(3, subProject.getStatus());

            preparedStatement.executeUpdate();
        }
    }

}
