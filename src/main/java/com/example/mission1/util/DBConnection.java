package com.example.mission1.util;

import java.sql.*;

/**
 * CREATE database public_wifi;
 *
 * create user 'public_wifi_user'@'localhost' identified by 'p1234';
 * grant all privileges on public_wifi.* to 'public_wifi_user'@'localhost';
 *
 * create user 'public_wifi_user'@'%' identified by 'p1234';
 * grant all privileges on public_wifi.* to 'public_wifi_user'@'%';
 */
public class DBConnection {
    private static Connection connection;

    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            String className = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/public_wifi?autoReconnect=true&allowPublicKeyRetrieval=true";
            String userName = "public_wifi_user";
            String password = "p1234";

            try {
                Class.forName(className);
                connection = DriverManager.getConnection(url, userName, password);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    public static void close(Connection con) {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Statement statement) {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection = null;
    }

    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection = null;
    }

    public static void close(Connection con, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connection = null;
    }
}
