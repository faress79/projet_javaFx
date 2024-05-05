package tn.esprit.pidev.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnector {

    private final String URL="jdbc:mysql://127.0.0.1:3306/projet_bd";
    private final String USERNAME ="root";
    private final String PASSWORD ="";
    private Connection cnx ;


    private static SQLConnector instance;

    private SQLConnector(){
        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            System.out.println("  " +
                    "Connected ... ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(" ___ Connection Failed ___");
        }
    }

    public static SQLConnector getInstance() {

        if(instance == null)
            instance = new SQLConnector();

        return instance;
    }

    public Connection getConnection() {
        return cnx;
    }


}
