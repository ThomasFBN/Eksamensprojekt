package Service;

import Model.User;
import Repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public void singUp(User user) throws SQLException {
        userRepository.signUp(user);
    }
}
