package com.example.eksamensprojekt.Service;

import com.example.eksamensprojekt.Model.User;
import com.example.eksamensprojekt.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public User checkLogin(String username, String password)throws SQLException{
      return userRepository.checkLogin(username, password);
    }
}
