package com.example.kursovaya;

import java.sql.*;

public class DataBaseConnection {
    private static final String LOGIN = "root";
    private static final String PASSWORD = "prarok3476";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/sys";

    public static Connection connection;
    public static Statement statement;

    public static void initialize() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION_URL, LOGIN, PASSWORD);
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
