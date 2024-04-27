package com.example.eksamensprojekt.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static Connection conn;
    private ConnectionManager(){

    }
    public static Connection getConnection(String db_url,String uid, String pwd){
        if (conn!=null) return conn;
        try {
            conn= DriverManager.getConnection(db_url, uid, pwd);

        } catch (SQLException e) {
            System.out.println("Failed to connect");
            e.printStackTrace();
        }
        return conn;
    }
}
