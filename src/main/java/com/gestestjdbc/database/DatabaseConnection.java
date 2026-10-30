package com.gestestjdbc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static final String DEFAULT_URL =
            "jdbc:mysql://10.127.168.128:3306/alumnos?serverTimezone=UTC";

    private static final String USER = "daw2";
    private static final String PASSWORD = "secreto";

    private DatabaseConnection() {
    }

//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(DEFAULT_URL, USER, PASSWORD);
//    }
public static Connection getConnection() throws SQLException {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new SQLException("No se encuentra MySQL Connector/J", e);
    }

    return DriverManager.getConnection(DEFAULT_URL, USER, PASSWORD);
}
}
