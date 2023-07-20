package com.example.pharmacymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection connectDb(){
        String databaseName = "pharmacy";
        String databaseUser = "root";
        String databasepassword = "";
        String url = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName, databaseUser, databasepassword);
            return connect;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
