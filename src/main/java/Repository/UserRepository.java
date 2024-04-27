package Repository;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private User user;

    public User signUp(User user) throws SQLException {
        try {
            Connection connection = Connection();
            String SQL = "INSERT INTO User ( username, password, role_Id) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(SQL);
          ps.setString(1, user.getUsername());
          ps.setString(2, user.getPassword());
          ps.setInt(3, user.getRole_ID());
          ResultSet rs = ps.executeQuery();

          if (rs.next()){
              int user_Id = rs.getInt(1);
              user.setUser_ID(user_Id);
          }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return user;
    }
}
