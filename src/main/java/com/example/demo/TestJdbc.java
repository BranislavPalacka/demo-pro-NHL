package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJdbc {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/nhl?useSSL=false";
        String user = "root";
        String pass = "root";

        try {
            System.out.println("connection to database  " + jdbcUrl);

            Connection myConn = DriverManager.getConnection(jdbcUrl,user,pass);
            System.out.println("connection success");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}