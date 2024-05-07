package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.Project;
import com.example.eksamensprojekt.Model.SubProject;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

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




}
