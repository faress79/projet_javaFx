package tn.esprit.projet3a.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static MyDatabase instance;
    private final  String URL = "jdbc:mysql://localhost:3306/3a53";
    private final  String USER = "root";
    private final  String PASSWORD = "";
    private Connection conncetion;
    private MyDatabase() {
        try {
            conncetion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connected");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }
    public static MyDatabase getInstance(){
        if (instance == null)
            instance = new MyDatabase();
        return instance;
    }

    public Connection getConnection() {
    return conncetion;}
}
