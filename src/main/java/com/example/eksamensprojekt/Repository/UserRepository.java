package com.example.eksamensprojekt.Repository;

import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.util.ConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {

    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String db_username;
    @Value("${spring.datasource.password}")
    private String db_password;

   public User findUser(String username) throws SQLException{
       Connection connection = ConnectionManager.getConnection(db_url, db_username, db_password);
       String SQL = "SELECT * FROM users WHERE username = ?";
       PreparedStatement ps = connection.prepareStatement(SQL);
       ps.setString(1,username);
       ResultSet rs = ps.executeQuery();

       User user= null;
       if (rs.next()){
           user = new User();
           user.setUsername(rs.getString("USERNAME"));
           user.setPassword(rs.getString("PASSWORD"));
           user.setUser_ID(rs.getInt("USER_ID"));
           user.setRole(rs.getString("ROLE"));

       }
       return user;
   }

   public User checkLogin(String username, String password)throws SQLException{
       User user = findUser(username);
       if (user!=null && user.getPassword().equals(password)){
           return  user;
       } else {
           return null;
       }

   }

   /* public User signUp(User user) throws SQLException {
        try {
            Connection connection = Connection();
            String SQL = "INSERT INTO User ( username, password, role_Id) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getRole_ID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int user_Id = rs.getInt(1);
                user.setUser_ID(user_Id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }*/
}
