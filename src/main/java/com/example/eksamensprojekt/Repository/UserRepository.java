package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private User user;
    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_username;
    @Value("${spring.datasource.password}")
    private String db_password;

    public User findUser(String username) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM users WHERE username = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        User user = null;
        if (rs.next()) {
            user = new User();
            user.setUsername(rs.getString("USERNAME"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setUser_ID(rs.getInt("USER_ID"));
            user.setRole(rs.getString("ROLE"));

        }
        return user;
    }

    public User checkLogin(String username, String password) throws SQLException {
        User user = findUser(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }

    }

    public void createUser(User user) throws SQLException {
        Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
        {
            String SQL = "INSERT INTO Users (username,password,role) VALUES (?,?,?)";
            try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getRole());

                int rowsAffected = ps.executeUpdate();
                ResultSet resultSet = ps.getGeneratedKeys();
                if (resultSet.next()) {
                    long generatedId = resultSet.getLong(1);
                    user.setUser_ID((int) generatedId);
                }
            }
        }
    }

    public List<User> showAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Connection connection = DriverManager.getConnection(db_url, db_username, db_password);
        String SQL = "SELECT * FROM Users";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUser_ID(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                userList.add(user);
            }

        }
        return userList;
    }

}






