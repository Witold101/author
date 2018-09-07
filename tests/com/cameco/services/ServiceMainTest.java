package com.cameco.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServiceMainTest {

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&characterEncoding=UTF-8&useSSL=false&useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
        String driver = "com.mysql.jdbc.Driver";
        String user = "root";
        String password = "wertywerty101";
        try {
            try {
                try {
                    Class.forName(driver).newInstance();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
